import argparse
import json
import os

import dash
import dash_bootstrap_components as dbc  # https://dash-bootstrap-components.opensource.faculty.ai/
import dash_core_components as dcc
import dash_html_components as html
import pandas as pd
import plotly.express as px
import plotly.graph_objects as go


def main():
    parser = argparse.ArgumentParser(description='PIE benchmark plotter')
    parser.add_argument(
        '--file',
        default='{}/../../../build/reports/jmh/result.json'.format(os.path.dirname(os.path.realpath(__file__))),
        type=str,
        help='JMH result file'
    )
    subparsers = parser.add_subparsers(title='subcommands', dest='subcommand')
    subparsers.add_parser('dash', help = 'Starts a live Dash application with benchmark results')
    subparsers.add_parser('export-html', help = 'Exports benchmark results as a static HTML page')
    args = parser.parse_args()

    data = create_long_form_dataframe_from_json(args.file)
    incrementality_fig = create_incrementality_figure(data)
    raw_data_fig = create_raw_data_figure(data)

    if args.subcommand == 'dash':
        start_dash_app(incrementality_fig, raw_data_fig)
    elif args.subcommand == 'export-html':
        export_html(incrementality_fig, raw_data_fig)


def create_long_form_dataframe_from_json(path: str) -> pd.DataFrame:
    with open(path) as json_file:
        json_data = json.load(json_file)
    series = []
    for bench_obj in json_data:
        series_dict = {}
        benchmark_name: str = bench_obj['benchmark']
        series_dict['benchmark_name'] = benchmark_name.replace("mb.pie.bench.spoofax3.Spoofax3Bench.", "")
        params_obj = bench_obj['params']
        series_dict['layer_name'] = params_obj['layerKind']
        for (full_metrics_name, metrics_obj) in bench_obj['secondaryMetrics'].items():
            full_metrics_name: str
            metric_name_colon_index: int = full_metrics_name.find(':')
            if metric_name_colon_index == -1: continue
            change_name: str = full_metrics_name[:metric_name_colon_index]
            metric_name: str = full_metrics_name[metric_name_colon_index + 1:]
            final_series_dict = series_dict.copy()
            final_series_dict['variable'] = metric_name
            final_series_dict['value'] = metrics_obj['score']
            final_series_dict['error'] = metrics_obj['scoreError']
            final_series_dict['unit'] = metrics_obj['scoreUnit']
            series.append(pd.Series(final_series_dict, name=change_name))
    data = pd.DataFrame(series)
    data.index.name = 'change'
    return data


def create_incrementality_figure(data: pd.DataFrame):
    facets = {
        'systemNanoTime': 'Time',
        'requiredTasks': 'Required tasks',
        'executedTasks': 'Executed tasks',
        'requiredResourceDependencies': 'Required resource dependencies',
        'providedResourceDependencies': 'Provided resource dependencies'
    }
    facet_keys = list(facets.keys())
    fig = px.bar(
        data[data.variable.isin(facet_keys)],
        y='value',
        error_y='error',
        color='benchmark_name',
        facet_row='variable',
        height=1500,
        category_orders={'variable': facet_keys}
    )
    fig.update_traces(texttemplate='%{value}')
    fig.update_traces(texttemplate='%{value:.2f}s', row=0)
    fig.update_layout(
        barmode='group',
        title='Incrementality comparison',
        legend=dict(title_text=None, orientation='h', yanchor='bottom', y=1.00, xanchor='right', x=1.00),
    )
    fig.for_each_annotation(lambda a: a.update(text=facets[a.text.split("=")[-1]]))
    fig.update_yaxes(matches=None, title=None)
    return fig


def create_raw_data_figure(data: pd.DataFrame):
    data = data.reset_index()
    fig = go.Figure(data=[go.Table(
        header=dict(
            values=list(data.columns),
            align='left'
        ),
        cells=dict(
            values=data.transpose().values.tolist(),
            align='left'
        )
    )])
    fig.update_layout(title='Raw data', height=1000)
    return fig


def start_dash_app(incrementality_fig, raw_data_fig):
    app = dash.Dash(external_stylesheets=[dbc.themes.BOOTSTRAP])
    app.layout = dbc.Container([
        html.H1("PIE benchmarks"),
        html.Hr(),
        single_row_col_graph(figure=incrementality_fig),
        single_row_col_graph(figure=raw_data_fig)
    ], fluid=True)
    app.run_server(debug=True)


def single_row_col(children):
    return dbc.Row(dbc.Col(html.Div(children)))


def single_row_col_graph(figure):
    return single_row_col(dcc.Graph(figure=figure))


def export_html(incrementality_fig, raw_data_fig):
    with open('result.html', 'w') as f:
        f.write(incrementality_fig.to_html(full_html=False, include_plotlyjs='cdn'))
        f.write(raw_data_fig.to_html(full_html=False, include_plotlyjs='cdn'))


if __name__ == "__main__":
    main()

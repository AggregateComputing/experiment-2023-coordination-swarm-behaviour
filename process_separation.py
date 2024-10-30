from plot_utils import *
import matplotlib
matplotlib.rcParams.update({'axes.titlesize': 20})
matplotlib.rcParams.update({'axes.labelsize': 18})
matplotlib.rcParams.update({'xtick.labelsize': 18})
matplotlib.rcParams.update({'ytick.labelsize': 18})
import matplotlib.pyplot as plt
if __name__ == '__main__':
    (means, stds) = run_chart_generation(
        'data',
        'charts',
        ['separationEval', 'separationEval30', 'separationEval120'],
        '{: 0.3f}',
        100,
        'time',
        ['random'],
        100,
        4000
    )
    convert_name = {
        'separationEval': 'separation=60m',
        'separationEval30': 'separation=30m',
        'separationEval120': 'separation=120m',
    }

    drop_probabilities = [0.0, 0.1, 0.2, 0.4, 0.7]
    kill_percentages = [0.0, 0.1, 0.2, 0.3]
    perception_errors = [0.0, 5.0, 10.0]
    #plot_chart(means, stds, drop_probabilities, kill_percentages, convert_name, 'errors', 'Separation', 'errors')
    print(means)
    plot_chart(means, stds, drop_probabilities, kill_percentages, perception_errors, convert_name, 'distance[mean]', 'Separation', 'distance')
        
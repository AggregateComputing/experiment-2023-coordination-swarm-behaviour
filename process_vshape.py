from plot_utils import *
import matplotlib
matplotlib.rcParams.update({'axes.titlesize': 20})
matplotlib.rcParams.update({'axes.labelsize': 18})
matplotlib.rcParams.update({'xtick.labelsize': 18})
matplotlib.rcParams.update({'ytick.labelsize': 18})
import matplotlib.pyplot as plt
## increase the font size

if __name__ == '__main__':
    (means, stds) = run_chart_generation(
        'data',
        'charts',
        ['vEval', 'vEval30', 'vEval60'],
        '{: 0.3f}',
        100,
        'time',
        ['random'],
        100,
        4000
    )
    convert_name = {
        'vEval': 'v shape=45°',
        'vEval30': 'v shape=30°',
        'vEval60': 'v shape=60°',
    }
    drop_probabilities = [0.0, 0.1, 0.2, 0.4, 0.7]
    kill_percentages = [0.0, 0.1, 0.2, 0.3]
    perception_errors = [0.0, 5.0, 10.0]

    plot_chart(means, stds, drop_probabilities, kill_percentages, perception_errors,  convert_name, 'errors', 'V', 'errors')
    plot_chart(means, stds, drop_probabilities, kill_percentages, perception_errors,  convert_name, 'angle[mean]', 'V', 'vertical')
        
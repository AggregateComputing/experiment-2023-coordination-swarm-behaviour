from plot_utils import *
import matplotlib.pyplot as plt
import matplotlib

# Consolidate matplotlib settings
matplotlib.rcParams.update({
    'axes.titlesize': 20,
    'axes.labelsize': 18,
    'xtick.labelsize': 18,
    'ytick.labelsize': 18
})

if __name__ == '__main__':
    means, stds = run_chart_generation(
        'data',
        'charts',
        ['circleEval', 'circleEvalLarge', 'circleEvalSmall'],
        '{: 0.3f}',
        100,
        'time',
        ['random'],
        100,
        4000
    )
    convert_name = {
        'circleEval': 'circle=120m',
        'circleEvalLarge': 'circle=160m',
        'circleEvalSmall': 'circle=80m',
    }
    drop_probabilities = [0.0, 0.1, 0.2, 0.4, 0.7]
    kill_percentages = [0.0, 0.1, 0.2, 0.3]
    perception_errors = [0.0, 5.0, 10.0]
    plot_chart(means, stds, drop_probabilities, kill_percentages, perception_errors, convert_name, 'errors', 'Circle', 'errors')
    plot_chart(means, stds, drop_probabilities, kill_percentages, perception_errors, convert_name, 'distance[mean]', 'Circle', 'distance')
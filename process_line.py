from plot_utils import *
import matplotlib.pyplot as plt
import matplotlib

matplotlib.rcParams.update({
    'axes.titlesize': 20,
    'axes.labelsize': 18,
    'xtick.labelsize': 18,
    'ytick.labelsize': 18
})

if __name__ == '__main__':
    means, stds = run_chart_generation('data', 'charts', ['lineEval', 'lineEval10', 'lineEval40'], '{: 0.3f}', 100, 'time', ['random'], 100, 4000)
    convert_name = {'lineEval': 'line=20m', 'lineEval10': 'line=10m', 'lineEval40': 'line=40m'}

    drop_probabilities = [0.0, 0.1, 0.2, 0.4, 0.7]
    kill_percentages = [0.0, 0.1, 0.2, 0.3]
    perception_errors = [0.0, 5.0, 10.0]

    plot_chart(means, stds, drop_probabilities, kill_percentages, perception_errors, convert_name, 'errors', 'Line', 'errors')
    plot_chart(means, stds, drop_probabilities, kill_percentages, perception_errors, convert_name, 'deviation[mean]', 'Line', 'vertical')
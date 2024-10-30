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
        ['consensusEval'],
        '{: 0.3f}',
        100,
        'time',
        ['random'],
        1,
        1200
    )
    convert_name = {
        'consensusEval': 'choices'
    }
    fail_probabilities = [0.0, 0.1, 0.2, 0.4, 0.7]
    kill_percentages = [0.0]
    perception_errors = [0.0, 5.0, 10.0]
    for fail_probability in fail_probabilities:
        for kill_percentage in kill_percentages:
            for perception_error in perception_errors:
                for label in means:
                    ds = means[label].sel(
                        fail_probability=fail_probability,
                        kill_percentage=kill_percentage,
                        perception_error=perception_error
                    )
                    std = stds[label].sel(
                        fail_probability=fail_probability,
                        kill_percentage=kill_percentage,
                        perception_error=perception_error
                    )
                    plt.fill_between(
                        ds['time'],
                        ds['choices'] - std['choices'],
                        ds['choices'] + std['choices'],
                        alpha=0.4,
                    )
                    plt.plot(ds['time'], ds['choices'], label=convert_name[label])

                plt.xlabel('Time')
                plt.ylabel('# unique choices')
                plt.title('Choices over time')
                plt.legend(fontsize=17, title_fontsize=20)
                plt.grid(True)
                plt.tight_layout()
                plt.savefig(f'charts/choices_fail_probability_{fail_probability}_kill_percentage_{kill_percentage}_perception_error_{perception_error}.pdf')
                plt.clf()

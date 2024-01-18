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
        ['lineEval', 'lineEval10', 'lineEval40'],
        '{: 0.3f}',
        100,
        'time',
        ['random'],
        100,
        1200
    )
    convert_name = {
        'lineEval': 'line=20m',
        'lineEval10': 'line=10m',
        'lineEval40': 'line=40m',
    }
    for label in means:
        ds = means[label]
        std = stds[label]
        plt.fill_between(
            ds['time'], 
            ds['deviation[mean]'] - std['deviation[mean]'],
            ds['deviation[mean]'] + std['deviation[mean]'],
            alpha=0.4,
        )
        plt.plot(ds['time'], ds['deviation[mean]'], label=convert_name[label])
    
    plt.xlabel('Time')
    plt.ylabel('Average vertical deviation')
    plt.title('Line')
    plt.legend(fontsize=17, title_fontsize=20)
    plt.grid(True)
    plt.tight_layout()
    plt.savefig('charts/lineChart.pdf')

        
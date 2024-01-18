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
        1200
    )
    convert_name = {
        'separationEval': 'separation=60m',
        'separationEval30': 'separation=30m',
        'separationEval120': 'separation=120m',
    }
    for label in means:
        ds = means[label]
        std = stds[label]
        plt.fill_between(
            ds['time'], 
            ds['distance[mean]'] - std['distance[mean]'],
            ds['distance[mean]'] + std['distance[mean]'],
            alpha=0.4,
        )
        plt.plot(ds['time'], ds['distance[mean]'], label=convert_name[label])
    
    plt.xlabel('Time')
    plt.ylabel('Average distance')
    plt.title('Separation')
    plt.legend(fontsize=17, title_fontsize=20)
    plt.grid(True)
    plt.tight_layout()
    plt.savefig('charts/separationEvalChart.pdf')
        
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
    for label in means:
        ds = means[label]
        std = stds[label]
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
    plt.legend(fontsize=16, title_fontsize=20)
    plt.grid(True)
    plt.tight_layout()
    plt.savefig('charts/consensus.pdf')

        
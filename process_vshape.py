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
        1200
    )
    convert_name = {
        'vEval': 'v shape[45°]',
        'vEval30': 'v shape[30°]',
        'vEval60': 'v shape[60°]',
    }
    for label in means:
        ds = means[label]
        std = stds[label]
        plt.fill_between(
            ds['time'], 
            ds['angle[mean]'] - std['angle[mean]'],  
            ds['angle[mean]'] + std['angle[mean]'], 
            alpha=0.4,
        )
        plt.plot(ds['time'], ds['angle[mean]'], label=convert_name[label])

    plt.xlabel('Time')
    plt.ylabel('Average Angle')
    plt.title('Angle w.r.t. leader')
    plt.legend(fontsize=16, title_fontsize=20)
    plt.grid(True)
    plt.tight_layout()
    plt.savefig('charts/vSpaceEval.pdf')
        
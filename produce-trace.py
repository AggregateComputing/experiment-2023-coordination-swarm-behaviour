# Adjusting the plotting code to use lighter colors for older values
import numpy as np
import json
import matplotlib.pyplot as plt

## Get all file from a folder passed via main and with a certain pattern
import glob
import os
import sys

plt.rcParams.update({'font.size': 35})
# Get the path from the command line
path = sys.argv[1]

# Get all files from the folder
files = glob.glob(os.path.join(path, "*.json"))
# Store in "charts" folder
charts_folder = os.path.basename("charts")
# Create a map that contains the experiment name and a list of the file. Given a file <experiment>-data-1.json, the key will be "experiment" and the value will be a list with the file experiment-data-1.json
files_map = {}
name_map = {
    'consensusEval': 'consensus',
    'circleEvalSmall': 'circle (distance=80m)',
    'circleEvalLarge': 'circle (distance=160m)',
    'circleEval': 'circle (distance=80m)',
    'separationEval120': 'separation (distance=120m)',
    'separationEval': 'separation (distance=60m)',
    'separationEval30': 'separation (distance=30m)',
    'lineEval': 'line (distance=20m)',
    'lineEval10': 'line (distance=10m)',
    'lineEval40': 'line (distance=40m)',
    'vEval': 'v shape (radius=45°)',
    'vEval30': 'v shape (radius=30°)',
    'vEval60': 'v shape (radius=60°)',
}
for file in files:
    # Get the file name
    file_name = os.path.basename(file)

    # Get the experiment name
    experiment_name = file_name.split("-")[0]

    # Add the file to the map as json
    with open(file) as json_file:
        json_data = json_file.read()
        json_data = json.loads(json_data)
        if experiment_name in files_map:
            files_map[experiment_name].append(json_data)
        else:
            files_map[experiment_name] = [json_data]


def plot_experiment(experiment_name, experiment, crop=500):
    # Initialize the plot
    plt.figure(figsize=(10, 10))

    # Iterate through each drone's data and plot
    for drone_id, positions in experiment.items():
        # Extracting x and y coordinates
        x_values = [position[0] for position in positions[::-1][:crop]]
        y_values = [position[1] for position in positions[::-1][:crop]]
        # Number of segments for each trajectory
        num_segments = len(x_values) - 1

        # Plotting each segment with a different color
        for i in range(num_segments):
            ## get color from the drone id using hue
            ## https://matplotlib.org/stable/gallery/color/named_colors.html
            hue = int(drone_id) * 3
            color = plt.get_cmap('viridis')(hue)
            ## Change alpha based the index of the segment
            alpha = (i/(num_segments))
            color = (color[0], color[1], color[2], alpha)
            # Plotting the segment
            ## change the linewidth based on the index of the segment
            linewidth = 3
            if(i == num_segments - 1):
                linewidth = 0.1
                plt.scatter(x_values[i], y_values[i], color=color, linewidth=linewidth)
            else:
                plt.plot(x_values[i:i+2], y_values[i:i+2], color=color, linewidth=linewidth)

    # Adding title and labels
    plt.title(name_map[experiment_name])
    plt.xlabel('X Coordinate')
    ## change the limits of the plot
    plt.ylabel('Y Coordinate')
    plt.grid(False)
    ## tigh layout
    plt.tight_layout()
    # Show the plot
    plt.savefig(os.path.join(charts_folder, experiment_name + ".pdf"))


def average_experiments(experiments):
    sum = {}
    for experiment in experiments:
        for drone_id, positions in experiment.items():
            if drone_id in sum:
                sum[drone_id] = np.add(sum[drone_id], positions)
            else:
                sum[drone_id] = positions
    average = {}
    for drone_id, positions in sum.items():
        average[drone_id] = positions / len(experiments)
    return average


for experiment_name, experiments in files_map.items():
    print("Plotting experiment", experiment_name)
    plot_experiment(experiment_name, average_experiments(experiments))

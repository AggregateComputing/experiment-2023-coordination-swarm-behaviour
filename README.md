<p align="center">
  <img src="https://user-images.githubusercontent.com/23448811/223975084-0bcc70fd-fdab-4ba9-a0ab-2f10f595e582.png">
</p>

<p align="center">
      <img width=20% src="https://user-images.githubusercontent.com/23448811/224010877-6f5c9d36-d348-4343-8b66-19f78778297e.gif">
      <img width=20% src="https://user-images.githubusercontent.com/23448811/224012332-290c81e1-effa-4cab-ae03-c603c116dd99.gif">
      <img width=20% src="https://user-images.githubusercontent.com/23448811/224012411-fbef5948-c546-49fa-b411-f5662831ef1b.gif">
      <img width=20% src="https://user-images.githubusercontent.com/23448811/224010942-178aea25-0fde-4bdd-b0e9-59709640cc30.gif">
      <img width=20% src="https://user-images.githubusercontent.com/23448811/224011009-411449cb-2b8e-4ebf-bc00-6fa8ba7a9120.gif">
      <img width=20% src="https://user-images.githubusercontent.com/23448811/224012578-d375de46-23c3-44e6-99cf-9d937548a1a5.gif">
      <img width=20% src="https://user-images.githubusercontent.com/23448811/224012699-0e29f217-66fb-44e7-b86e-85f6265e695e.gif">
      <img width=20% src="https://user-images.githubusercontent.com/23448811/224012742-b765aa73-dd31-4ffb-91a3-93c06e8b2750.gif">
</p>

Macro Swarm is a field-based libraries for expressing swarm behaviors in a declarative way.
It is built on top of the Aggregate computing framework, which is a distributed computing framework for the JVM.
Particularly, it is built of top of [ScaFi](https://scafi.github.io/), a scala library for programming aggregate computing systems. 
The following simulation are built on top of [Alchemist](http://alchemistsimulator.github.io/) -- a meta-simulator for pervasive like systems.

This is the companion repository for the paper sent at COORDINATION 2023 entitled: 
*MacroSwarm: a Field-based Compositional Framework for Swarm Programming*

## Requirements
For running the examples, the simulations and the chart generation, you need to have the following installed:

- A valid JDK installation (>= 11)
- A python installation (>= 3.8)

## Running the examples
The examples are located in the [src/main/scala/examples](src/main/scala/examples) folder.
Each example has an associated yaml file, which contains the configuration for the Alchemist simulation.
In the following we briefly describe how to run each example and what it is the expected result.

For enabling the trace, follow the following video:
<p align="center">
<img width=50% src="https://user-images.githubusercontent.com/23448811/225930534-7074d1ea-c79d-401b-835c-31d9a0961ddb.gif">
</p>


<details>
  <summary>Constant movement</summary>

| Example | Description | Command |
|--------| --- | --- |
| [src/main/scala/examples/ConstantMovement](src/main/scala/examples/ConstantMovement) | A swarm of agents moving in a straight line | `./gradlew runConstantMovementGraphic` |
<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/224010877-6f5c9d36-d348-4343-8b66-19f78778297e.gif">
</p>
</details>

<details>
  <summary>Explore</summary>

| Example | Description                              | Command |
|--------|------------------------------------------| --- |
| [src/main/scala/examples/Explore](src/main/scala/examples/Explore) | A swarm of agents exploring a fixed area | `./gradlew runConstantMovementGraphic` |

<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/224011009-411449cb-2b8e-4ebf-bc00-6fa8ba7a9120.gif">
</p>
</details>

<details>
  <summary>Obstacle Avoidance</summary>

| Example | Description                         | Command                                                                                         |
|--------|-------------------------------------|-------------------------------------------------------------------------------------------------|
| [src/main/scala/examples/ObstacleAvoidance](src/main/scala/examples/ObstacleAvoidance) | A swarm that try to avoid obstacles | `./gradlew runObstacleAvoidanceBigGraphic` or `./gradlew runObstacleAvoidanceMiddleGraphic` or `./gradlew runObstacleAvoidanceGraphic` |

<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/224012332-290c81e1-effa-4cab-ae03-c603c116dd99.gif">
</p>
</details>

<details>
  <summary>Towards Leader</summary>

| Example | Description                              | Command                             |
|--------|------------------------------------------|-------------------------------------|
| [src/main/scala/examples/TowardsLeader](src/main/scala/examples/TowardsLeader) | Nodes go towards a sink point (a leader) | `./gradlew runTowardsLeaderGraphic` |

<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/225931657-d0244288-cb74-491a-b27a-610e9bef70cc.gif">
</p>
</details>

<details>
  <summary>Spin Around a Leader</summary>

| Example | Description               | Command                          |
|--------|---------------------------|----------------------------------|
| [src/main/scala/examples/BranchingExample](src/main/scala/examples/BranchingExample) | Nodes spin aroud a leader | `./gradlew runSpinAroundGraphic` |

<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/224012411-fbef5948-c546-49fa-b411-f5662831ef1b.gif">
</p>
</details>

<details>
  <summary>Reynolds Flocking</summary>

| Example | Description                          | Command                            |
|--------|--------------------------------------|------------------------------------|
| [src/main/scala/examples/ReynoldFlock](src/main/scala/examples/ReynoldFlock) | Swarm moving following reynolds rule | `./gradlew runReynoldFlockGraphic` |

<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/224012578-d375de46-23c3-44e6-99cf-9d937548a1a5.gif">
</p>
</details>

<details>
  <summary>Team Formation (branching)</summary>

| Example | Description                              | Command                                |
|--------|------------------------------------------|----------------------------------------|
| [src/main/scala/examples/BranchingExample](src/main/scala/examples/BranchingExample) | Example of team formation through branch | `./gradlew runBranchingExampleGraphic` |

<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/224010942-178aea25-0fde-4bdd-b0e9-59709640cc30.gif">
</p>

</details>

<details>
  <summary>Team Formation (logical)</summary>

| Example | Description                                                        | Command                             |
|--------|--------------------------------------------------------------------|-------------------------------------|
| [src/main/scala/examples/TeamFormation](src/main/scala/examples/TeamFormation) | A swarm that create several sub-swarm based on spatial constraints | `./gradlew runTeamFormationGraphic` |

<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/225940078-2b28543c-dd0c-4b0b-9442-0edcfdc13a2f.gif">
</p>

</details>
<details>
  <summary>Shape Formation</summary>

| Example | Description                               | Command                        |
|--------|-------------------------------------------|--------------------------------|
| [src/main/scala/examples/AllShape](src/main/scala/examples/AllShape) | A swarm of nodes that form several shapes | `./gradlew runAllShapeGraphic` |

<p align="center">
    <img width=80% src="https://user-images.githubusercontent.com/23448811/225940078-2b28543c-dd0c-4b0b-9442-0edcfdc13a2f.gif">
</p>

</details>

For other examples, please refer to the [examples](src/main/yaml) folder. For each file, you can run the corresponding example with the following command:

```bash
./gradlew run<ExampleName>Graphic
```

### Simulation: Find and Rescue
With this API, we propose a use case study highlighting the ability of MacroSwarm to express complex
swarm behaviors. In our scenario, we want a fleet of drones to patrol a spatial
area of 1km^2. In this environment, dangerous situations may arise (e.g., a fire
breaks out, a person gets injured, etc.). 
In response to these situations, a drone
designated as a healer must approach the danger and resolve it. 
Exploration must be carried out in groups composed of at least one healer and several explorers,
who will help the healer identify alarm situations. 
Initially, 50 explorers and 5 healers are randomly positioned in this area. Each drone has a maximum speed
of approximately 20 km/h and a communication range of 100 meters. The alarm
situations are randomly generated at different times within the spatial area in
a [0, 50] minutes (simulated) timeframe. 
Each simulation run lasts 90 minutes, during which we expect the number of alarm situations to reach a minimum value

For more details, please refer to the companion paper.
For seeing the dynamics of the simulation, you can run the following command:

```bash
./gradlew runRescueGraphic
````

In the following there is a sequence of screenshots of the simulation:

<img width=32% src="https://user-images.githubusercontent.com/23448811/232057651-42a7edfe-c812-4b92-9ec9-9f899d56b90e.png">
<img width=32% src="https://user-images.githubusercontent.com/23448811/232057757-d0410536-f379-4675-8bfd-a6bf15e3cb19.png">
<img width=32% src="https://user-images.githubusercontent.com/23448811/232057379-1efacf38-04cb-4f77-9744-a2e72746fdc4.png">

#### Reproduce the results
To reproduce the results of the paper, you can run the following command:

```bash
./gradlew runRescueBatch
```
This will launch 64 simulations with different seeds. Each of them, will produce a csv file in the `data` folder.
In this repository, the data is already loaded, 
so you can directly run the following command to generate the plots:
```bash
pip install -r requirements.txt
python plotter.py
```
This will produce the following charts stored in charts/:

<img width=32% src="https://user-images.githubusercontent.com/23448811/232056242-7152346b-e6e7-4eb6-b8a9-c72c3cd6ada1.png">
<img width=32% src="https://user-images.githubusercontent.com/23448811/232056530-c6b14064-b1d5-4a38-8642-dc6d2be063ec.png">
<img width=32% src="https://user-images.githubusercontent.com/23448811/232056744-8b04d3d7-2430-454e-bdce-498ddb347069.png">

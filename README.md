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
Particularly, it is built of top of [ScaFi](), a scala library for programming aggregate computing systems. 
The following simulation are built on top of [Alchemist]() -- a meta-simulator for pervasive like systems.

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

<details>
  <summary>Constant movement</summary>

| Example | Description | Command |
|--------| --- | --- |
| [src/main/scala/examples/ConstantMovement](src/main/scala/examples/ConstantMovement) | A swarm of agents moving in a straight line | `./gradlew runConstantMovementGraphic` |

<img width=100% src="https://user-images.githubusercontent.com/23448811/224010877-6f5c9d36-d348-4343-8b66-19f78778297e.gif">

</details>
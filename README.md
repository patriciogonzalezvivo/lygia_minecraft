# LYGIA version for Minecraft Mods

# Table of Contents

- [Introduction](#introduction)
- [Setup](#setup)
- [Build](#build)
- [License](#license)


## Introduction

A minecraft port of [Lygia](https://github.com/patriciogonzalezvivo/lygia)

## Setup

To set up the Lygia MineCraft mod development environment you must first clone the repo and initialize the submodules.

```bash
git clone --recurse-submodules https://github.com/patriciogonzalezvivo/lygia_minecraft.git
```

## Build

Building the project is as easy as running a Gradle command!

In windows simply run:
```bash
gradlew build
```

Whiele in linux or mac run:
```bash
chmod +x gradlew
./gradlew build
```

and the outputted `.jar` (or `.zip`) files will be put in `build/libs/` folder of each subproject (`fabric/build/libs/`, `forge/build/libs/` and `resourcepack/build/libs/`).

## Testing

You can test shaders using te `testmod` project, simply replace a vanilla shader in the `src/resources/` folder and run the following command to run the game and test it.
```bash
gradlew Client
```

## License

LYGIA is dual-licensed under the [Prosperity License](https://prosperitylicense.com/versions/3.0.0) and the [Patron License](https://lygia.xyz/license) for [sponsors](https://github.com/sponsors/patriciogonzalezvivo) and [contributors](https://github.com/patriciogonzalezvivo/lygia/graphs/contributors).

[Sponsors](https://github.com/sponsors/patriciogonzalezvivo) and [contributors](https://github.com/patriciogonzalezvivo/lygia/graphs/contributors) are automatically added to the [Patron License](https://lygia.xyz/license) and they can ignore any non-commercial rule of the [Prosperity License](https://prosperitylicense.com/versions/3.0.0) software (please take a look at the exceptions).

It's also possible to get a permanent commercial license hooked to a single and specific version of LYGIA.
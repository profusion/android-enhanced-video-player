# Contributing

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Setup](#setup)
  - [Pre-requirements](#pre-requirements)
  - [Install Node](#install-node)
  - [Install JS Dependencies](#install-js-dependencies)
  - [Install Ktlint to IDE](#install-ktlint-to-ide)
- [Running the project](#running-the-project)

## Setup

### Pre-requirements

For the javascript runtime, we recommend [Node.js](https://nodejs.org/en/).

We **strongly** suggest you to use [NVM \(Node Version Manager\)](https://github.com/nvm-sh/nvm) and also doing [this configuration](https://github.com/nvm-sh/nvm#deeper-shell-integration).

To ensure compatibility with [Android Gradle plugin](https://developer.android.com/studio/releases#android_gradle_plugin_and_android_studio_compatibility), it is mandatory to use Android Studio Flamingo or any subsequent version.

We use yarn for dependency management, install instructions are available at https://classic.yarnpkg.com/lang/en/.

### Install Node

```console
$ nvm install
$ nvm use
```

Every time you use the repo you will need to use `nvm use` in your terminal to commit

### Install JS Dependencies

```console
$ yarn install
```

This will install husky pre-commit, pre-push and msg-commit hooks.

### Install Ktlint to IDE

To ensure code quality, the project utilizes [kotlinter](https://github.com/jeremymailen/kotlinter-gradle) for linting. The linting process is automatically triggered through commit and push hooks. However, you can also perform manual linting by running the commands `yarn lint` and `yarn lint-fix`.

For a seamless integration with Android Studio and to conveniently view and fix linting errors within the IDE, we highly recommend installing the [ktlint-intellij-plugin](https://plugins.jetbrains.com/plugin/15057-ktlint-unofficial-). This plugin allows you to directly display and handle linting errors from within Android Studio.

**Pro Tip:** use a hotkey to make Android Studio format your file for you. You can choose your hotkey on `File > Settings > Keymap > Main Menu > Code > Reformat Code & Reformat File`

## Running the project

To run the project, open it in the IDE, Android Studio, and execute it.

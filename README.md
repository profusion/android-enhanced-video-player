# android-enhanced-video-player
Enhanced Video Player for Android built on top of Exoplayer compliant with Android Jetpack Compose

## Table of Contents

- [Table of Contents](#table-of-contents)
- [Setup](#setup)
  - [Pre-requirements](#pre-requirements)
  - [Install Node](#install-node)
  - [Install Ruby Dependencies](#install-ruby-dependencies)
  - [Install JS Dependencies](#install-js-dependencies)
  - [Install Ktlint to IDE](#install-ktlint-to-ide)
- [Running the project](#running-the-project)

## Setup

### Pre-requirements

For the javascript runtime, we recommend [Node.js](https://nodejs.org/en/).
We **strongly** suggest you to use [NVM \(Node Version Manager\)](https://github.com/nvm-sh/nvm) and also doing [this configuration](https://github.com/nvm-sh/nvm#deeper-shell-integration).
We **strongly** suggest you to use [rbenv (Managing Ruby environment)](https://github.com/rbenv/rbenv).


We use yarn for dependency management, install instructions are available at https://classic.yarnpkg.com/lang/en/.

### Install Node

```console
$ nvm install
$ nvm use
```

Every time you use the repo you will need to use `nvm use` in your terminal to commit

### Install Ruby Dependencies

```console
$ rbenv install
$ bundle install
```

This will install Ruby and fastlane, used for linting the project.

### Install JS Dependencies

```console
$ yarn install
```

This will install husky pre-commit, pre-push and msg-commit hooks.

### Install Ktlint to IDE
Make sure to open this project with Android Studio at least once before proceeding with ktlint instalation. That guarantees some hidden files/folders (like `.idea`) exist and can be configured by the following command. Navigate to the project’s directory in the terminal and execute:
```console
ktlint applyToIDEAProject
```

This will change Android Studio's code format configurations. The next time you apply an automatic code formatting on a kotlin file, it will use our new set of rules defined by ktlint.

**Pro Tip:** use a hotkey to make Android Studio format your file for you. You can choose your hotkey on `File > Settings > Keymap > Main Menu > Code > Reformat Code & Reformat File`

## Running the project

To run the project, open it in the IDE, Android Studio, and execute it.

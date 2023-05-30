# How to make a Release

- [How to make a Release](#how-to-make-a-release)
  - [Updating](#updating)
    - [Updating Java Version](#updating-java-version)
  - [Versioning](#versioning)
  - [Changelog](#changelog)
    - [Style](#style)
    - [Template](#template)
  - [Releasing Steps](#releasing-steps)


## Updating

New features and bugfixes PRs should always be made against `main` branch.

### Updating Java Version

When updating the Java version don't forget to update also `jitpack.yml` to make use of the correct JDK because JitPack sometimes has a hard time defining which image it should use

## Versioning

The version should always be changed in the `gradle.properties` file, following the [Semantic Versioning 2.0.0](https://semver.org/#semantic-versioning-200), in short:

Version format - MAJOR.MINOR.PATCH

 - MAJOR: Incompatible API Changes
 - MINOR: Backward compatible new functionality
 - PATCH: Backward compatible bug fixes

### Style

For consistency, all CHANGELOG entries should follow a common style:

- Use `##` for the version line. A version line should have a blank line before and after it.
- Use `-` for individual items.
- Entries should end with a `.`.
- Breaking changes should be introduced with **BREAKING CHANGE**:, or **BREAKING CHANGES**: if there is a sub-list of changes.


## Releasing Steps

1. Create a `release/{version}` branch bumping `VERSION_NAME` in `gradle.properties` and open a PR.
2. When merged, on `Code` tab at GitHub, go to Releases on the right-hand side
3. Click on [Create a new release](https://github.com/profusion/android-enhanced-video-player/releases/new)
4. Create a new version based on the Semantic Versioning
5. Click on `Generate Release Notes`
6. Click on `Publish new Release`
7. Check if new version was updated on [JitPack](https://jitpack.io/#profusion/android-enhanced-video-player)
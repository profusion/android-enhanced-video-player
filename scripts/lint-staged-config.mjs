import micromatch from 'micromatch';
import path from 'path';

const kotlinExtension = '*.kt';
const kotlinFilePattern = '**/' + kotlinExtension;
const jsFilePattern = '**/*.(ts|tsx|js|jsx|mjs)';

function handleKotlinFiles(allStagedFiles) {
  const matches = micromatch(allStagedFiles, [kotlinFilePattern]);
  const commands = [];

  if (matches.length > 0) {
    const relativeMatches = matches.map((match) =>
      path.relative(process.cwd(), match),
    );

    const relativeMatchesString = relativeMatches.join('\n');
    commands.push(
      `yarn lint-fix -p . -PinternalKtlintGitFilter="\n${relativeMatchesString}\n"`,
    );
  }

  return commands;
}

function handleJSFiles(allStagedFiles) {
  const matches = micromatch(allStagedFiles, [jsFilePattern]);
  const actions = matches.reduce((acc, item) => {
    acc.push('yarn prettier --check ' + item);
    return acc;
  }, []);

  return actions.length > 0 ? actions : null;
}

const patternHandlers = [handleKotlinFiles, handleJSFiles];

export default (allStagedFiles) => {
  const commands = patternHandlers.reduce((acc, handler) => {
    const subcommands = handler(allStagedFiles);
    if (subcommands !== null) {
      return acc.concat(subcommands);
    } else {
      return acc;
    }
  }, []);

  return commands;
};

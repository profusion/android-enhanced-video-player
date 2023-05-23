import micromatch from 'micromatch';

const kotlinExtension = '*.kt';
const kotlinFilePattern = '**/' + kotlinExtension;
const jsFilePattern = '**/*.(ts|tsx|js|jsx|mjs)';

function handleKotlinFiles(allStagedFiles) {
  const matches = micromatch(allStagedFiles, [kotlinFilePattern]);
  const commands = [];
  const networkFiles = '**/data/network/' + kotlinExtension;
  const networkFilesMatches = micromatch(allStagedFiles, [networkFiles]);

  if (matches.length > 0) {
    // Returns a single action for all kotlin files. This is an optimization
    // to garantee that only a single gradle task is executed (which will lint
    // all staged files at once).
    commands.push('yarn lint-from-staged');
  }

  // Running Auth tests (which are instrumented) is expensive, thus
  // only run when the network package changes
  if (networkFilesMatches.length > 0) {
    commands.push('yarn auth-test');
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

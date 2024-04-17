package commands;

import exceptions.CommandExecutingException;
import exceptions.WrongDataException;
import utils.DataPreparer;
import utils.InstructionFetcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ExecuteScriptCommand extends CHCommand{
    public ExecuteScriptCommand(String commandName, String description,
                                boolean hasInlineArguments, boolean isMultiLines) {
        super(commandName, description, hasInlineArguments, isMultiLines);
    }

    public List<String> prepareData(String [] args, Command [] commands, HashSet<String> scriptFiles) throws FileNotFoundException, CommandExecutingException, WrongDataException {
        //
        List<String> prepData = new ArrayList<>();
        InstructionFetcher instructionFetcher = new InstructionFetcher(commands);
        Scanner fileScanner = new Scanner(new File(args[0]));
        String line;
        scriptFiles.add(args[0]);
        while (fileScanner.hasNextLine()) {
            line = fileScanner.nextLine();
            String[] command = line.split(" ");
            String [] argsToGive = Arrays.copyOfRange(command, 1, command.length);
            if (Objects.equals(line, "")){
                continue;
            }
            Command currentCommand = instructionFetcher.instructionFetch(command[0]);
            if ((CHCommand.class.isAssignableFrom(currentCommand.getClass()))) {
                if (Objects.equals(currentCommand.getCommandName(), "execute_script")){
                    if (argsToGive.length == 0){
                        throw new CommandExecutingException("Не передано имя файла");
                    }
                    if (scriptFiles.contains(argsToGive[0])){
                        throw new CommandExecutingException("Скрипт порождает рекурсию");
                    }
                    ExecuteScriptCommand scriptCommand = (ExecuteScriptCommand) currentCommand;
                    List<String> scriptData = scriptCommand.prepareData(argsToGive, commands, scriptFiles);
                    prepData.addAll(scriptData);
                    scriptFiles.remove(argsToGive[0]);
                }
                else if (Objects.equals(currentCommand.getCommandName(), "history")){
                    prepData.add("history");
                }
                else if (Objects.equals(currentCommand.getCommandName(), "help")){
                    prepData.add("help");
                }
            }
            else {

                if (!currentCommand.isMultiLines()){
                    DataPreparer.prepareScriptData(currentCommand, argsToGive, fileScanner);
                    prepData.add(line);
                }
                else {
                    String [] sd = DataPreparer.prepareScriptData(currentCommand, argsToGive, fileScanner);
                    String [] argsForCommand = Arrays.copyOfRange(sd, 1, sd.length);
                    List<String> argsForCommandFinal = Arrays.asList(argsForCommand);
                    prepData.add(line);
                    prepData.addAll(argsForCommandFinal);
                }
            }
        }
        return prepData;
    }

}

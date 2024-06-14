package ru.example.javashells.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.example.javashells.commands.ClsCommand;
import ru.example.javashells.commands.ExitCommand;
import ru.example.javashells.commands.HelpCommand;
import ru.example.javashells.commands.catalogues.CdCommand;
import ru.example.javashells.commands.catalogues.CopyCommand;
import ru.example.javashells.commands.catalogues.CrFileCommand;
import ru.example.javashells.commands.catalogues.CrdirCommand;
import ru.example.javashells.commands.catalogues.DelCommand;
import ru.example.javashells.commands.catalogues.LsCommand;
import ru.example.javashells.components.managers.DirectoryManager;
import ru.example.javashells.interfaces.Command;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandConfig {

	@Bean
	public DirectoryManager directoryManager() {
		return new DirectoryManager(new File(System.getProperty("user.dir")));
	}

	@Bean
	public Map<String, Command> commandMap(DirectoryManager directoryManager) {

		CrdirCommand crdirCommand = new CrdirCommand(directoryManager);
		CdCommand cdCommand = new CdCommand(directoryManager);
		LsCommand lsCommand = new LsCommand(directoryManager);
		CrFileCommand crFileCommand = new CrFileCommand(directoryManager);
		DelCommand delCommand = new DelCommand(directoryManager);
		CopyCommand copyCommand = new CopyCommand(directoryManager);

		Map<String, Command> commandMap = new HashMap<>();
		
		commandMap.put(cdCommand.getName(), cdCommand);
		commandMap.put(crdirCommand.getName(), crdirCommand);
		commandMap.put(lsCommand.getName(), lsCommand);
		commandMap.put(crFileCommand.getName(), crFileCommand);
		commandMap.put(delCommand.getName(), delCommand);
		commandMap.put(copyCommand.getName(),copyCommand);

		commandMap.put("exit", new ExitCommand());
		commandMap.put("help", new HelpCommand());
		commandMap.put("cls", new ClsCommand());
		return commandMap;
	}
}

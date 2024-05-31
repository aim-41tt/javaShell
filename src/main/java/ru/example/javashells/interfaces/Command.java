package ru.example.javashells.interfaces;

public interface Command {
	String getName();
	void execute(String [] args);
}

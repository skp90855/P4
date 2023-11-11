package edu.uga.cs.p4;

import java.lang.reflect.Method;

public class ThreadInstructionHandler<T> {

    String instruction;
    T object;

    public ThreadInstructionHandler(){}

    public ThreadInstructionHandler(String instruction, T object) {
        this.instruction = instruction;
        this.object = object;
    }

    public void executeInstruction() {
        if(instruction.equals("read")) {
            //object.readFromDB();
            try {
                Method m = object.getClass().getMethod("readFromDB");
                m.invoke(object);
            } catch(Exception e) {
                System.out.println("error was caught when invoking read from db method");
            }
        } else {
            //object.writeToDB();
            try {
                Method m = object.getClass().getMethod("writeToDB");
                m.invoke(object);
            } catch(Exception e) {
                System.out.println("error was caught when invoking write to db method");
            }
        }
    }
}

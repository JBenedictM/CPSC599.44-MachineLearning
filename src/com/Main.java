package com;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


interface Task{ }

/**
 * Primary Driver class for CPSC599.44-MachineLearning project. Initializes logging structures for
 * overall application in this static class. This class has the following attributes:
 * <ul>
 *     <li>{@link #LOGGER}</li>
 *     <li>{@link #VERBOSE}</li>
 *     <li>{@link #CONFIG}</li>
 *     <li>{@link #INPUT}</li>
 *     <li>{@link #OUTPUT}</li>
 *     <li>{@link #WEIGHTS}</li>
 * </ul>
 *
 * TODO declare configurations file: config.txt and specify format.
 * TODO make logger messages receivable from alternative executing classes.
 * TODO declare task format for constructing task list.
 * TODO Load input learners and make constructor to combined learned results from multiple training phases
 *
 * @author Andrew Burton
 * @version %I%, %G%
 * @since 1.0
 */
public class Main {

    /** The {@link Logger} identifier used to setup logger streams for recording data messages.*/
    private static Logger LOGGER = Logger.getLogger("RuleTrainer");
    /** {@link String} regular expression to match verbose flag and capture groups.*/
    private static final String VERBOSE = "(?:(?:-v|--verbose)\\s([0-7]))";
    /** {@link String} regular expression to match config flag.*/
    private static final String CONFIG = "(-c|--config)";
    /** {@link String} regular expression to match input flag and capture filename group*/
    private static final String INPUT = "(?:(?:-i|--input)\\s(\\S+\\.txt))";
    /** {@link String} regular expression to match output flag and capture filename group*/
    private static final String OUTPUT = "(?:(?:-o|--output)\\s(\\S+\\.txt))";
    /** {@link String} regular expression to match weights and capture number list group*/
    private static final String WEIGHTS = "(?:(?:-w|--weights)\\s((?:\\s*(?:\\d+))+))";

    /**
     * The main function is the default runner for this application. This function will be responsible for
     * receiving error codes and logging to the appropriate location, as well as controlling program flow.
     *
     * @param args commandline arguments provided at runtime
     */
    public static void main(String[] args) {
        List<? extends Task> taskList;
        try {
            setupLogger();
            if (args.length < 3) {
                LOGGER.severe("Usage: java -jar AppName -v level [-c | -i input -o output -w weights]");
                System.exit(0);
            } else {
                List<String> argParse = parseArgs(args);
                setVerbosity(argParse.get(1));
                taskList = (argParse.get(2) == null) ? runSettings() : runManySettings();
                LOGGER.info("Begin execution flow of tasks:");
                for (Object task : taskList) trainModel(task);
                // handle program execution here
            }
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
            System.exit(-1);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                                //
    //                                       PROGRAM EXECUTION FUNCTIONS                                              //
    //                                                                                                                //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Estimated difficulty/time: Easy / 0.5 Hours
     * TODO load single configuration
     * @return a list of one element containing the configuration settings for a single training model
     */
    private static List<Task> runSettings() {
        LOGGER.entering(Main.class.getName(), "runSettings()");
        LinkedList<Task> result = new LinkedList<>();
        LOGGER.exiting(Main.class.getName(), "runSettings()", result);
        return result;
    }

    /**
     * Estimated difficulty/time: Easy / 0.5 Hours
     * TODO load many simulation configurations from config file
     * @return a list of many configurations for a training model
     */
    private static List<Task> runManySettings() {
        LOGGER.entering(Main.class.getName(), "runManySettings()");
        LinkedList<Task> result = new LinkedList<>();
        LOGGER.exiting(Main.class.getName(), "runManySettings()", result);
        return result;
    }

    /**
     * Estimated difficulty/time:  Intermediate / 2 hours
     * TODO run model simulation here
     * @param task configuration settings provided for a model training simulation
     */
    private static void trainModel(Object task) {
        LOGGER.entering(Main.class.getName(), "trainModel(Object task)", task);
        // do the thing
        LOGGER.exiting(Main.class.getName(), "trainModel(Object task)");
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                                //
    //                                       ARGUMENT PARSING FUNCTIONS                                               //
    //                                                                                                                //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * The parseArgs function is provided a list of arguments provided to the application, and transforms it
     * into a map of flags and values. Values are returned in the following order
     * <ol>
     *     <li>Verbosity level: as an integer  in the range 0(no messages) to 7(all messages)</li>
     *     <li>Config: if this option is selected program will read config file and take no other arguments (namely input, output, and weights)</li>
     *     <li>Input: input file name with optional path if default directory is not sufficient</li>
     *     <li>Output: output file name with optional path if default directory is not sufficient</li>
     *     <li>Weights: list of integer weights to provide a given bias value to the program in order to probe certain paths or program flows</li>
     * </ol>
     *
     * @param args commandline arguments passed from main
     * @return the list of arguments parsed by their assigned flags
     */
    private static List<String> parseArgs(String[] args) {
        Pattern pattern = Pattern.compile("\\s*"+VERBOSE+"\\s"+"(?:"+CONFIG+"|(?:"+INPUT+"\\s"+OUTPUT+"\\s"+WEIGHTS+"))\\s*");
        Matcher matcher = pattern.matcher(String.join(" ", args));
        List<String> matches = new LinkedList<>();
        while (matcher.find()) matches.add(matcher.group());
        return matches;
    }

    private static void setVerbosity(String level) {
        switch (level) {
            case "0": LOGGER.setLevel(Level.OFF);     break;
            case "1": LOGGER.setLevel(Level.SEVERE);  break;
            case "2": LOGGER.setLevel(Level.WARNING); break;
            case "3": LOGGER.setLevel(Level.INFO);    break;
            case "4": LOGGER.setLevel(Level.FINE);    break;
            case "5": LOGGER.setLevel(Level.FINER);   break;
            case "6": LOGGER.setLevel(Level.FINEST);  break;
            default:  LOGGER.setLevel(Level.ALL);     break;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                                                                                //
    //                                         LOGGER SETUP FUNCTIONS                                                 //
    //                                                                                                                //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Method to create handler objects for the log controller, in order to specify where to write log
     * messages.
     *
     * @throws IOException pass error upwards from contained methods
     */
    private static void setupLogger() throws IOException {
        LOGGER.setUseParentHandlers(false);
        Formatter formatter = setupFormatLog();
        setupConsoleLog(formatter);
        setupFileLog(formatter);
        LOGGER.info("Logger Initialized");
    }

    /**
     * Setup the logger handler for writing log messages to the console.
     *
     * @param formatter sets structure for log messages
     */
    private static void setupConsoleLog(Formatter formatter) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        LOGGER.addHandler(consoleHandler);
    }

    /**
     * Setup the logger handler for writing log messages to a given file for the associated class
     *
     * @param formatter sets structure for log messages
     * @throws IOException caused by an invalid access to the logs/main.log log file
     */
    private static void setupFileLog(Formatter formatter) throws IOException {
        FileHandler fileHandler = new FileHandler("logs/main.log", true);
        fileHandler.setFormatter(formatter);
        LOGGER.addHandler(fileHandler);
    }

    /**
     * A simple method designed to create a basic structure for the log messages containing date, time, calling class,
     * calling method, the message level, as well as the provided log message.  These details can be used to help pinpoint
     * the source of a given problem.
     *
     * @return formatter object to set the default shape of each logged message
     */
    private static Formatter setupFormatLog() {
        return new Formatter() {
            @Override
            public String format(LogRecord record) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                Calendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(record.getMillis());
                return record.getLevel()
                        + " - ["
                        + simpleDateFormat.format(calendar.getTime())
                        + "] || "
                        + record.getSourceClassName().substring(record.getSourceClassName().indexOf(".") + 1)
                        + "."
                        + record.getSourceMethodName()
                        + "() : "
                        + record.getMessage()
                        + "\n";
            }
        };
    }
}

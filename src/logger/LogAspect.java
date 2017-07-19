package logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Klasa tworz¹ca logi za pomoc¹ AspectJ
 * @author mina
 *
 */
@Aspect
public class LogAspect 
{
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Aspekt tworzacy log przed wywo³aniem funkcji ConnJPA.AddUser
	 * @param joinPoint
	 */
	@Before("execution(* chat.ConnJPA.AddUser(..))")
	public void logBeforeAdd(JoinPoint joinPoint) {
		for(Object arg : joinPoint.getArgs()) {
		System.out.println("[LOG] zalogowano u¿ytkownika o nicku " + (String)arg);
		LOGGER.setLevel(Level.INFO);
        LOGGER.info("Zalogowano u¿ytkownika o nicku " + (String)arg);
		}
	}
	/**
	 * Aspekt tworzacy log przed wywo³aniem funkcji ConnJPA.LogoutUser
	 * @param joinPoint
	 */
	@Before("execution(* chat.ConnJPA.LogoutUser(..))")
	public void logBeforeLogout(JoinPoint joinPoint) {
		for(Object arg : joinPoint.getArgs()) {
		System.out.println("[LOG] wylogowano u¿ytkownika o nicku " + (String)arg);
		LOGGER.setLevel(Level.INFO);
        LOGGER.info("Wylogowano u¿ytkownika o nicku " + (String)arg);
		}
	}	
}
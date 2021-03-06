package logger;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Klasa tworząca logi za pomocą AspectJ
 * @author mina
 *
 */
@Aspect
public class LogAspect 
{
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/**
	 * Aspekt tworzacy log przed wywołaniem funkcji ConnJPA.AddUser
	 * @param joinPoint
	 */
	@Before("execution(* chat.ConnJPA.AddUser(..))")
	public void logBeforeAdd(JoinPoint joinPoint) {
		for(Object arg : joinPoint.getArgs()) {
		System.out.println("[LOG] zalogowano użytkownika o nicku " + (String)arg);
		LOGGER.setLevel(Level.INFO);
        LOGGER.info("Zalogowano użytkownika o nicku " + (String)arg);
		}
	}
	/**
	 * Aspekt tworzacy log przed wywołaniem funkcji ConnJPA.LogoutUser
	 * @param joinPoint
	 */
	@Before("execution(* chat.ConnJPA.LogoutUser(..))")
	public void logBeforeLogout(JoinPoint joinPoint) {
		for(Object arg : joinPoint.getArgs()) {
		System.out.println("[LOG] wylogowano użytkownika o nicku " + (String)arg);
		LOGGER.setLevel(Level.INFO);
        LOGGER.info("Wylogowano użytkownika o nicku " + (String)arg);
		}
	}	
}
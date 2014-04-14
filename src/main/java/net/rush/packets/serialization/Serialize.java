package net.rush.packets.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Serialize {
	int order();

	Type type();

	/**
	 * @return A Serialize that contains more info.
	 * Very ugly, but I'm unable to find a better solution :/
	 */
	int moreInfo() default -1;
}

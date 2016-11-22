package gonqbox;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A FP-inspired utility class for returning whether a function succeeded, a value if it did,
 * and an error value that may be of a different type if it failed.
 * <br><br>
 * Based largely on Rust's Result type, which is itself inspired by OCaml and Haskell's Either type.
 * <br><br>
 * Currently extremely incomplete, although there isn't any reason to implement the other main features
 * for this project. Notably, there ought to be an 'expect' (or 'expectOk and expectErr) function that
 * returns the ok value or throws an unchecked exception, 'mapOk' and 'mapErr', that can change the
 * type and contents of the appropriate value, and, of course, 'andThen', that chains
 * Function<OkType, Result<T, ErrType>s together, which is one of the places that this class would
 * become rather nice to have.
 *
 * For example,
 * <code>
 * DB.openConnection()
 *     .andThen(con -> readUser(id))
 *     .andThen(user -> user.doSomehing());
 * </code>
 */
public class Result<OkType, ErrType> {
	private final OkType okVal;
	private final ErrType errVal;
	private final boolean isOk;

	private Result(boolean isOk, OkType ok, ErrType err) {
		assert isOk? err == null : ok == null;
		this.isOk = isOk;
		okVal = ok;
		errVal = err;
	}

	public static <S, F> Result<S, F> ok(S val) {
		return new Result<S, F>(true, val, null);
	}

	public static <S, F> Result<S, F> err(F val) {
		return new Result<S, F>(false, null, val);
	}

	public void ifErr(Consumer<ErrType> fn) {
		if(!isOk)
			fn.accept(errVal);
	}

	public void ifOk(Consumer<OkType> fn) {
		if(isOk)
			fn.accept(okVal);
	}

	public void match(Consumer<OkType> okFn, Consumer<ErrType> errFn) {
		if(isOk)
			okFn.accept(okVal);
		else
			errFn.accept(errVal);
	}

	public <R> R match(Function<OkType, R> okFn, Function<ErrType, R> errFn) {
		if(isOk)
			return okFn.apply(okVal);
		else
			return errFn.apply(errVal);
	}
}

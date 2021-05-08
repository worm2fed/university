/**
 * Created by worm2fed on 22.03.17.
 */
class DuplicateItemException extends RuntimeException {
    DuplicateItemException() {
        super();
    }

    DuplicateItemException(String mes) {
        super(mes);
    }
}

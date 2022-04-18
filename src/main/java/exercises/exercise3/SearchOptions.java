package exercises.exercise3;

import java.util.Objects;

public class SearchOptions {

    protected boolean countOnly ;
    protected boolean ignoreCase;


    protected boolean recursive = false;

    public boolean isRecursive() {
        return recursive;
    }

    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }


    public boolean isCountOnly() {
        return countOnly;
    }

    public void setCountOnly(boolean countOnly) {
        this.countOnly = countOnly;
    }

    public boolean isIgnoreCase() {
        return ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

}

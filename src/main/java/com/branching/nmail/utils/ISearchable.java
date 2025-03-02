package com.branching.nmail.utils;

import java.util.List;

public interface ISearchable<T> {

    /*
     * Returns the list of fields were a match was found
     * */
    public List<T> search(int userId, String searchText, boolean caseSensitive);
}

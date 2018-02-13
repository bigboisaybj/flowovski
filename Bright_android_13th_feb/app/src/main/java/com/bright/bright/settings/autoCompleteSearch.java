package com.bright.bright.settings;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import com.bright.bright.R;

public class autoCompleteSearch {

    Long testValue = 0L;
    Long primeValueForTerm = 0L;
    ArrayList<String> holidaysListWithUpperCase = new ArrayList<>(Arrays.asList("Last Day of Hanukkah", "New Year's Day", "Ephiphany", "Emphiphany", "Ephimphany", "Ephiphquestrian", "Ephiphanx", "Orthodox Christmas Day", "Australia Day", "Chinese New Year", "Tu B'Shevat", "Arbor Day", "Royal Hobart Regatta", "Valentine's Day", "Shrove Tuesday", "Ash Wedensday", "Labour Day", "Purim", "Eight Hours Day", "Adelaide Cup", "Canberra Day", "St Patrick's Day", "March equinox", "Harmony Day", "Palm Sunday", "First day of Passover", "Maundy Thursday", "Orthodox Good Friday", "Good Friday", "Orthodox Holy Saturday", "Holy Saturday", "Orthodox Easter", "Easter Day", "Orthodox Easter Monday", "Last day of Passover", "Easter Tuesday", "Yom HaShoah", "Isra and Mi'raj", "ANZAC Day", "May Day", "Yom HaAtzmaut", "Lag B'Omer", "Mother's Day", "Ascension Day", "National Sorry Day", "Ramadan", "Shavuot", "Pentecost", "Whit Monday", "Western Australia Day", "Queensland Day", "Trinity Sunday", "Queen's Birthday", "Corpus Christi", "Laylat al-Qadar", "Night of Destiny", "June Solstice", "Eid-al-Fitr", "First day of NAIDOC Week", "Tisha B'Av", "New South Wales Bank Holiday", "Northern Territory Picnic Day", "Assumption of Mary", "Royal Agricultural Show Day Queensland", "Eid-al-Adha", "Father's Day", "Rosh Hashana", "Muharram New Year", "September Equinox", "Family and Community Day", "AFL Grand Final Friday", "Yom Kippur", "Feast of St. Francis of Assisi", "First day of Sukkot", "Last day of Sukkot", "Shmini Atzeret", "Simchat Torah", "Diwali", "Halloween", "All Saints' Day", "All Souls' Day", "Recreation Day", "Melbourne Cup Day", "Remembrance Day", "Prophet's Birthday", "First Sunday of Advent", "Feast of the Immaculate Conception", "First Day of Hanukkah", "December Solstice", "Christmas Eve", "Christmas Day", "Boxing Day", "New Years's Eve"));
    ArrayList<String> searchTermsWithUpperCase = new ArrayList<>(Arrays.asList("Experience", "Address", "Photos", "Business", "Menu", "Menu Vitus", "Advertise", "At-Home", "Account", "Diet", "Banking", "About"));
    ArrayList<Long> singleValueDictionary = new ArrayList<Long>();
    ArrayList<String> singleTermDictionary = new ArrayList<String>();

    ArrayList<String> MultiTermTitle = new ArrayList<>();
    ArrayList<ArrayList<Long>> MultiTermPrime = new ArrayList<>();
    ArrayList<Long> primesArray = new ArrayList<>(Arrays.asList(2L, 3L, 5L, 7L, 11L, 13L, 17L, 19L, 23L, 29L, 31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 71L, 73L, 79L, 83L, 89L, 97L, 101L));
    ArrayList<Character> primesChar = new ArrayList<>(Arrays.asList('e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 'l', 'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z', ' '));

    ArrayList<Long> cachePrimeValues = new ArrayList<>();
    ArrayList<String> cacheTitles = new ArrayList<>();
    ArrayList<String> cacheTitlesMulti = new ArrayList<>();
    ArrayList<ArrayList<Long>> cachePrimeValuesMulti = new ArrayList<>();

    ArrayList<Integer> toBeRemoved;

    public autoCompleteSearch(String encodeList) {
        if (encodeList.equals("HOLIDAYS")) {
            encodeHolidaysList();
        }
        else if (encodeList.equals("SEARCH")) {
            Log.i("TAG", "search called");
            encodeSearchTerms();
        }
    }

    private void encodeSearchTerms() {
        boolean addedToMulti = false;

        for (int itemInList = 0; itemInList < searchTermsWithUpperCase.size(); itemInList++) {
            String lowerCaseHolder = searchTermsWithUpperCase.get(itemInList).toLowerCase();

            for (int characterInItem = 0; characterInItem < searchTermsWithUpperCase.get(itemInList).length();characterInItem++) {
                char lowerCaseHolderCharItem = lowerCaseHolder.charAt(characterInItem);

                if (Character.isWhitespace(lowerCaseHolderCharItem)) {
                    addedToMulti = true;
                    packageMultiTermForSearchGeneral(itemInList, characterInItem, primeValueForTerm);
                    primeValueForTerm = 0L;
                    break;
                }

                for (int z = 0; z < 26;z++) {
                    if (lowerCaseHolderCharItem == primesChar.get(z)) {
                        if (primeValueForTerm == 0) {
                            primeValueForTerm = primesArray.get(z);
                        }
                        else {
                            primeValueForTerm = primeValueForTerm * primesArray.get(z);
                        }
                    }
                }
            }
            if (primeValueForTerm == 0L) {
            }
            else if (!addedToMulti) {
                singleValueDictionary.add(primeValueForTerm);
                singleTermDictionary.add(searchTermsWithUpperCase.get(itemInList));
            }
            primeValueForTerm = 0L;
            addedToMulti = false;
        }
    }

    public void encodeHolidaysList() {
        boolean addedToMulti = false;

        for (int itemInHolidayList = 0; itemInHolidayList < holidaysListWithUpperCase.size(); itemInHolidayList++) {
            String lowerCaseHolder = holidaysListWithUpperCase.get(itemInHolidayList).toLowerCase();

            for (int characterInItem = 0; characterInItem < holidaysListWithUpperCase.get(itemInHolidayList).length();characterInItem++) {
                char lowerCaseHolderCharItem = lowerCaseHolder.charAt(characterInItem);

                if (Character.isWhitespace(lowerCaseHolderCharItem)) {
                    addedToMulti = true;
                    packageMultiTermForHolidays(itemInHolidayList, characterInItem, primeValueForTerm);
                    primeValueForTerm = 0L;
                    break;
                }

                for (int z = 0; z < 26;z++) {
                    if (lowerCaseHolderCharItem == primesChar.get(z)) {
                        if (primeValueForTerm == 0) {
                            primeValueForTerm = primesArray.get(z);
                        }
                        else {
                            primeValueForTerm = primeValueForTerm * primesArray.get(z);
                        }
                    }
                }
            }
            if (primeValueForTerm == 0L) {
            }
            else if (!addedToMulti) {
                singleValueDictionary.add(primeValueForTerm);
                singleTermDictionary.add(holidaysListWithUpperCase.get(itemInHolidayList));
            }
            primeValueForTerm = 0L;
            addedToMulti = false;
        }
    }

    private void packageMultiTermForSearchGeneral(int listIndex, int indexOfFirstWhiteSpace, Long primeValueForTerm) {
        Long tempHolderPrimeCount = 0L;

        ArrayList<Long> tempHolder = new ArrayList<>();
        tempHolder.add(primeValueForTerm);

        String multiLowerCaseHolder = searchTermsWithUpperCase.get(listIndex).toLowerCase();

        for (int i = indexOfFirstWhiteSpace; i < multiLowerCaseHolder.length(); i++) {
            if (Character.isWhitespace(multiLowerCaseHolder.charAt(i))) {
                if (i != indexOfFirstWhiteSpace) {
                    tempHolder.add(tempHolderPrimeCount);
                    tempHolderPrimeCount = 0L;
                }
            }
            for (int z = 0; z < 26; z++) {
                if (multiLowerCaseHolder.charAt(i) == primesChar.get(z)) {
                    if (tempHolderPrimeCount == 0) {
                        tempHolderPrimeCount = primesArray.get(z);
                    } else {
                        tempHolderPrimeCount = tempHolderPrimeCount * primesArray.get(z);
                    }
                }
            }
        }
        tempHolder.add(tempHolderPrimeCount);
        MultiTermTitle.add(searchTermsWithUpperCase.get(listIndex));
        MultiTermPrime.add(tempHolder);
    }

    private void packageMultiTermForHolidays(int holidayListIndex, int indexOfFirstWhiteSpace, Long primeValueForTerm) {
        Long tempHolderPrimeCount = 0L;

        ArrayList<Long> tempHolder = new ArrayList<>();
        tempHolder.add(primeValueForTerm);

        String multiLowerCaseHolder = holidaysListWithUpperCase.get(holidayListIndex).toLowerCase();

        for (int i = indexOfFirstWhiteSpace; i < multiLowerCaseHolder.length(); i++) {
            if (Character.isWhitespace(multiLowerCaseHolder.charAt(i))) {
                if (i != indexOfFirstWhiteSpace) {
                    tempHolder.add(tempHolderPrimeCount);
                    tempHolderPrimeCount = 0L;
                }
            }
            for (int z = 0; z < 26; z++) {
                if (multiLowerCaseHolder.charAt(i) == primesChar.get(z)) {
                    if (tempHolderPrimeCount == 0) {
                        tempHolderPrimeCount = primesArray.get(z);
                    } else {
                        tempHolderPrimeCount = tempHolderPrimeCount * primesArray.get(z);
                    }
                }
            }
        }
        tempHolder.add(tempHolderPrimeCount);
        MultiTermTitle.add(holidaysListWithUpperCase.get(holidayListIndex));
        MultiTermPrime.add(tempHolder);
    }

    public Long encodeInput(CharSequence inputLetters) {
        testValue = 0L;

        String testString = inputLetters.toString().toLowerCase();

        for (int i = 0; i < testString.length(); i++) {
            for (int t = 0; t < 26; t++) {
                if (testString.charAt(i) == ' ') {
                    //TODO: INCLUDE WHAT HAPPENS IF ENTRY IS A SPACE.
                }

                if (testString.charAt(i) == primesChar.get(t)) {
                    if (testValue == 0) {
                        testValue = primesArray.get(t);
                    }
                    else {
                        testValue = testValue * primesArray.get(t);
                    }
                }
            }
        }
        return testValue;
    }

    public void search(CharSequence input) {
        if (input.length() > 0) {
            if (input.charAt(0) == ' ') {
            } else {
                Long userInput = encodeInput(input);

                if (cacheTitles.isEmpty() && cacheTitlesMulti.isEmpty()) {
                    searchDictionary(userInput);
                    searchMultiTermDictionary(userInput);
                    currentListings();
                }
                else if (cacheTitles.isEmpty()) {
                    searchMultiTermCache(userInput);
                    searchDictionary(userInput);
                    searchMultiTermDictionary(userInput);
                    currentListings();
                }
                else if (cacheTitlesMulti.isEmpty()) {
                    searchCache(userInput);
                    searchDictionary(userInput);
                    searchMultiTermDictionary(userInput);
                    currentListings();
                }
                else if (!cacheTitles.isEmpty() && !cacheTitlesMulti.isEmpty()){
                    searchMultiTermCache(userInput);
                    searchCache(userInput);
                    //TODO: ONLY SEARCH DICTIONARIES IF CACHES ARE EMPTY.

                    searchDictionary(userInput);
                    searchMultiTermDictionary(userInput);
                    currentListings();
                }
                syntaxStructure(input);
            }
        }
    }

    public void currentListings() {
        for (int t = 0; t < cacheTitles.size(); t++) {
            Log.i("TAG", "Cache contains: " + cacheTitles.get(t));
        }
        for (int t = 0; t < cacheTitlesMulti.size(); t++) {
            Log.i("TAG", "MultiCache contains: " + cacheTitlesMulti.get(t));
        }
    }

    public void searchDictionary(Long userInput) {
        for (int i = 0; i < singleValueDictionary.size(); i++) {
            if (userInput == 0L) {
                return;
            }
            if ((singleValueDictionary.get(i) % userInput) == 0) {
                addToCache(singleTermDictionary.get(i), singleValueDictionary.get(i));
            }
            else {
                checkCacheResults(userInput);
            }
        }
    }

    public void searchMultiTermDictionary(Long userInput) {
        for (int q = 0; q < MultiTermPrime.size(); q++) {
            for (int b = 0; b < MultiTermPrime.get(q).size(); b++) {
                if (MultiTermPrime.get(q).get(b) % userInput == 0) {
                    addToMultiCache(q);
                }
                else {
                    checkMultiCacheResults(userInput);
                }
            }
        }
    }

    public void searchCache(Long userInput) {
        for (int j = 0; j < cachePrimeValues.size(); j++) {
            if ((cachePrimeValues.get(j) % userInput) == 0) {
            }
            else {
                checkCacheResults(userInput);
            }
        }
    }

    public void searchMultiTermCache(Long userInput) {
        for (int z = 0; z < cachePrimeValuesMulti.size(); z++) {
            for (int y = 0; y < cachePrimeValuesMulti.get(z).size(); y++) {
                if (cachePrimeValuesMulti.get(z).get(y) % userInput == 0) {
                }
                else {
                    if (!checkMultiCacheResults(userInput)) {
                        clearToBeRemovedMultiCacheItems(toBeRemoved);
                        break;
                    }
                }
            }
        }
    }

    public void addToCache(String titleOfMatch, Long valueoOfMatch) {
        boolean match = false;
        boolean reachedEnd = false;

        if (cacheTitles.isEmpty()) {
            cacheTitles.add(titleOfMatch);
            cachePrimeValues.add(valueoOfMatch);
        }
        else {
            for (int i = 0; i < cacheTitles.size(); i++) {
                if (cacheTitles.get(i).equals(titleOfMatch)) {
                    match = true;
                }
                if (i == cacheTitles.size()) {
                    reachedEnd = true;
                }
                if (match) {
                    return;
                }
                else if (!match && reachedEnd){
                    cacheTitles.add(titleOfMatch);
                    cachePrimeValues.add(valueoOfMatch);
                    return;
                }
            }
        }
    }

    public void addToMultiCache(int indexOfMultiItemHit) {
        boolean multiMatch = false;
        boolean reachedMultiEnd = false;

        if (cacheTitlesMulti.isEmpty()) {
            cacheTitlesMulti.add(MultiTermTitle.get(indexOfMultiItemHit));
            cachePrimeValuesMulti.add(MultiTermPrime.get(indexOfMultiItemHit));
        }
        else {
            for (int i = 0; i < cacheTitlesMulti.size();i++) {
                if (cacheTitlesMulti.get(i).equals(MultiTermTitle.get(indexOfMultiItemHit))) {
                    multiMatch = true;
                }

                if (i == cacheTitlesMulti.size()-1) {
                    reachedMultiEnd = true;
                }

                if (multiMatch) {
                    return;
                }
                else if (!multiMatch && reachedMultiEnd) {
                    cacheTitlesMulti.add(MultiTermTitle.get(indexOfMultiItemHit));
                    cachePrimeValuesMulti.add(MultiTermPrime.get(indexOfMultiItemHit));
                    return;
                }
            }
        }
    }

    public void checkCacheResults(Long encodedUserInput) {
        if (!cacheTitles.isEmpty()) {
            for (int i = 0; i < cacheTitles.size(); i++) {
                if (cachePrimeValues.get(i) % encodedUserInput != 0) {
                    cacheTitles.remove(i);
                    cachePrimeValues.remove(i);
                }
            }
        }
    }

    public boolean checkMultiCacheResults(Long encodedUserInput) {
        boolean indexMatch = false;

        toBeRemoved = new ArrayList<>();

        if (!cachePrimeValuesMulti.isEmpty()) {
           for (int i = 0; i < cachePrimeValuesMulti.size(); i++) {
               for (int j = 0; j < cachePrimeValuesMulti.get(i).size(); j++) {
                   if (cachePrimeValuesMulti.get(i).get(j) % encodedUserInput != 0) {
                       toBeRemoved.add(i);
                       indexMatch = true;
                   }

                   if ((j == cachePrimeValuesMulti.get(i).size()-1) && indexMatch) {
                       return false;
                   }
               }
           }
        }
        return true;
    }

    public void clearToBeRemovedMultiCacheItems(ArrayList<Integer> indexOfToBeRemovedItems) {
        for (int i = 0; i < indexOfToBeRemovedItems.size(); i++) {
            int indexToRemove = indexOfToBeRemovedItems.get(i);

            if (!cacheTitlesMulti.isEmpty() && !cachePrimeValuesMulti.isEmpty()) {
                cacheTitlesMulti.remove(indexToRemove);
                cachePrimeValuesMulti.remove(indexToRemove);
            }
        }
    }

    public String SuggestionsFromSearch() {
        Log.i("TAG", "openedSuggestions");

        if (cacheTitles.size() > 0) {
            for (int i = 0; i < cacheTitles.size(); i++) {
                if ("Christmas Eve".equals(cacheTitles.get(i))) {
                    return "christmas";
                } else if ("Easter Day".equals(cacheTitles.get(i))) {
                    return "easter";
                } else {
                    Log.i("TAG", "cache does not include roots");
                }
            }
        }

        if (cacheTitlesMulti.size() > 0) {
            for (int t = 0; t < cacheTitlesMulti.size(); t++) {
                if ("Christmas Eve".equals(cacheTitlesMulti.get(t))) {
                    return "christmas";
                } else if ("Easter Day".equals(cacheTitlesMulti.get(t))) {
                    return "easter";
                } else {
                    Log.i("TAG", "Multicache does not include roots");
                }
            }
        }
        return "No root matches";
    }

    public void clearCaches() {
        cacheTitles.clear();
        cachePrimeValues.clear();
        cacheTitlesMulti.clear();
        cachePrimeValuesMulti.clear();
    }

    public void syntaxStructure(CharSequence uncodedUserInput) {
        checkSyntaxStructureCache(uncodedUserInput);

        //checkMultiCacheResults(userInput);
        //checkCacheResults(userInput);

    }

    public void checkSyntaxStructureCache(CharSequence uncodedUserInput) {

        //TODO: CHECK WHETHER THE STRUCTURE OF EACH TERM MATCHES THE USER'S.
        //IF SO, THEN GIVE POINTS TO THAT ENTRY.
        //GENERALLY, TERMS WITH THE SAME STRUCTURE AS INPUT SHOULD RANK HIGHER THAN THOSE THAT DON'T.

    }
}
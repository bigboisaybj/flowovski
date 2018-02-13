package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import com.bright.bright.R;

public class settings_Home_SecondRow_fragment extends Fragment implements settings_HomeAdapter.SettingsListAdapterListener{

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private settings_HomeAdapter adapter;
    private RecyclerView.Adapter advertiseAdapter;
    private ArrayList<String> titleDataSet;
    private ArrayList<String> settingTitleData;
    private ArrayList<Integer> imageDataSet;
    private advertiseData advertData;
    private Timer timer;
    private final long DELAY = 30;

    openVenueRegistration mOpenVenueRego;
    autoCompleteSearch searchLink;
    settingsTitles settingsTitlesForAdapter;
    registerTopRow mRegister;
    slideTopRow mExpandTopRow;
    slideTopRow mSlideTopRow;
    View secondRowLayout;
    View settingsViewOnMain;
    String NextChildCardAnimation_For_Animator = "SLIDE DOWN";
    String presentCard = "DEFAULT";
    Boolean childCardDown = false;
    boolean encodedSearchTerms = false;
    boolean suggestionsSearchActivated = false;
    boolean ScreenSlidUp = false;
    boolean accountChildrenShown = false;
    boolean expandedTopAndBottom = false;
    View cardMid;
    View searchFunction;
    EditText searchEditText;
    float businessAlpha = 1.0f;
    float advertiseAlpha= 0.5f;
    float atHomeAlpha = 1f;
    float accountAlpha = 0.5f;
    float dietAlpha = 1.0f;
    float bankingAlpha = 0.5f;
    float aboutAlpha = 1.0f;
    TextView settingsTitleOnHome;
    TextView suggestionOne;
    TextView suggestionTwo;
    TextView suggestionThree;
    TextView titleBusiness;
    TextView titleAdvertise;
    TextView titleAtHome;
    TextView titleAccount;
    TextView titleDiet;
    TextView titleBanking;
    TextView titleAbout;
    ImageButton suggestionLeft;
    ImageButton suggestionCentre;
    ImageButton suggestionRight;
    ScrollView secondRowScroll;
    LinearLayoutManager linearLayoutManager;
    boolean createAdAdapter;

    public String [] title = {"Business", "Account", "Diet Filters", "Payment", "Help", "About", "Careers", "hold"};
    public int[] images = {R.drawable.square_orange, R.drawable.square_blue, R.drawable.square_lightblue, R.drawable.square_orange, R.drawable.square_lightblue, R.drawable.square_grey, R.drawable.square_lightblue, R.drawable.square_blue, R.drawable.square_blue};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        secondRowLayout = inflater.inflate(R.layout.settings_home_scrollable_secondrow, container, false);

        advertData = new advertiseData();

        settingsTitleOnHome = (TextView) getActivity().findViewById(R.id.SettingsTitleMain);
        suggestionOne = secondRowLayout.findViewById(R.id.suggestionOne);
        suggestionTwo = secondRowLayout.findViewById(R.id.suggestionTwo);
        suggestionThree = secondRowLayout.findViewById(R.id.suggestionThree);
        suggestionLeft = secondRowLayout.findViewById(R.id.suggestionImageButtonLeft);
        suggestionCentre = secondRowLayout.findViewById(R.id.suggestionImageButtonCentre);
        suggestionRight = secondRowLayout.findViewById(R.id.suggestionImageButtonRight);

        settingsTitlesForAdapter = new settingsTitles();
        for (int i = 0; i < title.length; i++) {
            settingsTitlesForAdapter.addTitle(title[i]);
        }

        settingTitleData = new ArrayList<>();
        for (int i = 0; i < settingsTitlesForAdapter.size(); i++) {
            settingTitleData.add(settingsTitlesForAdapter.getTitle(i));
        }

        titleDataSet = new ArrayList<>();
        for (int i = 0; i < title.length; i++) {
            titleDataSet.add(title[i]);
        }

        imageDataSet = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            imageDataSet.add(images[i]);
        }

        titleBusiness = (TextView) secondRowLayout.findViewById(R.id.titleBusiness);
        titleAdvertise  = (TextView) secondRowLayout.findViewById(R.id.titlePayment);
        titleAtHome  = (TextView) secondRowLayout.findViewById(R.id.titleDietFilters);
        titleAccount  = (TextView) secondRowLayout.findViewById(R.id.titlePayment);
        titleDiet  = (TextView) secondRowLayout.findViewById(R.id.titleHelp);
        titleBanking  = (TextView) secondRowLayout.findViewById(R.id.titleCareers);
        titleAbout  = (TextView) secondRowLayout.findViewById(R.id.titleCareers);

        recyclerView = (RecyclerView) secondRowLayout.findViewById(R.id.recycler_view_SecondRow);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.setLayoutManager(layoutManager);
        adapter = new settings_HomeAdapter(titleDataSet,imageDataSet,this);
        DefaultItemAnimator def = new DefaultItemAnimator();
        def.setAddDuration(3000);
        recyclerView.setItemAnimator(def);
        recyclerView.setAdapter(adapter);

        titleAdvertise.setAlpha(0.5f);
        titleAccount.setAlpha(0.5f);
        titleBanking.setAlpha(0.5f);

        secondRowScroll = (ScrollView) secondRowLayout.findViewById(R.id.settingsSecondRowScrollView);
        secondRowScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (businessAlpha >= 1f) {
                     businessAlpha = 1f;
                }
                if (advertiseAlpha >= 1f) {
                    advertiseAlpha = 1f;
                }
                if (atHomeAlpha >= 1f) {
                    atHomeAlpha = 1f;
                }
                if (accountAlpha >= 1f) {
                    accountAlpha = 1f;
                }
                if (dietAlpha >= 1f) {
                    dietAlpha = 1f;
                }
                if (bankingAlpha >= 1f) {
                    bankingAlpha = 1f;
                }
                if (aboutAlpha >= 1f) {
                    aboutAlpha = 1f;
                }
                if (businessAlpha <= 0.1f) {
                    businessAlpha = 0.1f;
                }
                if (advertiseAlpha <= 0.1f) {
                    advertiseAlpha = 0.1f;
                }
                if (atHomeAlpha <= 0.1f) {
                    atHomeAlpha = 0.1f;
                }
                if (accountAlpha <= 0.1f) {
                    accountAlpha = 0.1f;
                }
                if (dietAlpha <= 0.1f) {
                    dietAlpha = 0.1f;
                }
                if (bankingAlpha <= 0.1f) {
                    bankingAlpha = 0.1f;
                }
                if (aboutAlpha <= 0.1f) {
                    aboutAlpha = 0.1f;
                }

                 if (scrollY > oldScrollY) {
                     if (scrollY >= 0 && scrollY < 56) {
                         advertiseHighlight();
                         businessAlpha -= 0.03f;
                         advertiseAlpha += 0.03f;
                         if (businessAlpha <= 0.1f) {
                             businessAlpha = 0.1f;
                         }
                         titleBusiness.setAlpha(businessAlpha);
                         titleAdvertise.setAlpha(advertiseAlpha);
                     }
                     if (scrollY >= 57 && scrollY < 118) {
                         atHomeHighlight();
                         recyclerView.scrollToPosition(1);
                         advertiseAlpha -= 0.03f;
                         atHomeAlpha += 0.05f;
                         if (advertiseAlpha <= 0.1f) {
                             advertiseAlpha = 0.1f;
                         }
                         titleAdvertise.setAlpha(advertiseAlpha);
                         titleAtHome.setAlpha(atHomeAlpha);
                     }
                     if (scrollY >= 119 && scrollY < 254) {
                         accountHighlight();
                         atHomeAlpha -= 0.02f;
                         accountAlpha += 0.03f;
                         if (atHomeAlpha <= 0.1f) {
                             atHomeAlpha = 0.1f;
                         }
                         titleAtHome.setAlpha(atHomeAlpha);
                         titleAccount.setAlpha(accountAlpha);
                     }
                     if (scrollY >= 255 && scrollY < 375) {
                         dietHighlight();
                         accountAlpha -= 0.03f;
                         dietAlpha += 0.03f;
                         if (accountAlpha <= 0.1f) {
                             accountAlpha = 0.1f;
                         }
                         titleAccount.setAlpha(accountAlpha);
                         titleDiet.setAlpha(dietAlpha);
                     }
                     if (scrollY >= 376 && scrollY < 506) {
                         bankingHighlight();
                         dietAlpha -= 0.03f;
                         bankingAlpha += 0.03f;
                         if (dietAlpha <= 0.1f) {
                             dietAlpha = 0.1f;
                         }
                         titleDiet.setAlpha(dietAlpha);
                         titleBanking.setAlpha(bankingAlpha);
                     }
                     if (scrollY >= 507 && scrollY < 638) {
                         aboutHighlight();
                         bankingAlpha -= 0.03f;
                         aboutAlpha += 0.03f;
                         if (bankingAlpha <= 0.1f) {
                             bankingAlpha = 0.1f;
                         }
                         titleBanking.setAlpha(bankingAlpha);
                         titleAbout.setAlpha(aboutAlpha);
                     }
                     if (scrollY >= 639) {
                         atHomeHighlight();
                         aboutAlpha += 0.03f;
                         if (aboutAlpha <= 0.1f) {
                             aboutAlpha = 0.1f;
                         }
                         titleAbout.setAlpha(aboutAlpha);
                     }
                 }
                if (scrollY < oldScrollY) {
                    if (scrollY >= 0 && scrollY < 56) {
                        businessHighlight();
                        businessAlpha += 0.05f;
                        advertiseAlpha -= 0.05f;
                        if (businessAlpha <= 0.1f) {
                            businessAlpha = 0.1f;
                        }
                        titleBusiness.setAlpha(businessAlpha);
                        titleAdvertise.setAlpha(advertiseAlpha);
                    }
                    if (scrollY >= 57 && scrollY < 118) {
                        advertiseHighlight();
                        advertiseAlpha += 0.1f;
                        atHomeAlpha -= 0.05f;
                        if (advertiseAlpha <= 0.1f) {
                            advertiseAlpha = 0.1f;
                        }
                        titleAdvertise.setAlpha(advertiseAlpha);
                        titleAtHome.setAlpha(atHomeAlpha);
                    }
                    if (scrollY >= 119 && scrollY < 254) {
                        atHomeHighlight();
                        atHomeAlpha += 0.05f;
                        accountAlpha -= 0.05f;
                        if (atHomeAlpha <= 0.1f) {
                            atHomeAlpha = 0.1f;
                        }
                        titleAtHome.setAlpha(atHomeAlpha);
                        titleAccount.setAlpha(accountAlpha);
                    }
                    if (scrollY >= 255 && scrollY < 375) {
                        accountHighlight();
                        accountAlpha += 0.05f;
                        dietAlpha -= 0.05f;
                        if (accountAlpha <= 0.1f) {
                            accountAlpha = 0.1f;
                        }
                        titleAccount.setAlpha(accountAlpha);
                        titleDiet.setAlpha(dietAlpha);
                    }
                    if (scrollY >= 376 && scrollY < 506) {
                        dietHighlight();
                        dietAlpha += 0.05f;
                        bankingAlpha -= 0.05f;
                        if (dietAlpha <= 0.1f) {
                            dietAlpha = 0.1f;
                        }
                        titleDiet.setAlpha(dietAlpha);
                        titleBanking.setAlpha(bankingAlpha);
                    }
                    if (scrollY >= 507 && scrollY < 638) {
                        bankingHighlight();
                        bankingAlpha += 0.05f;
                        aboutAlpha -= 0.05f;
                        if (bankingAlpha <= 0.1f) {
                            bankingAlpha = 0.1f;
                        }
                        titleBanking.setAlpha(bankingAlpha);
                        titleAbout.setAlpha(aboutAlpha);
                    }
                    if (scrollY >= 639) {
                        //bankingHighlight();
                        aboutAlpha += 0.05f;
                        if (aboutAlpha <= 0.1f) {
                            aboutAlpha = 0.1f;
                        }
                        titleAbout.setAlpha(aboutAlpha);
                    }
                }
                }
        });

        searchEditText = (EditText) secondRowLayout.findViewById(R.id.searchEdit);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                String presentText = s.toString();

                if ((s.length() == 0)) {
                    searchLink.clearCaches();
                    searchContraction();
                    searchEditText.getText().clear();
                    searchEditText.clearFocus();
                }

                if (s.length() > 0) {
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!encodedSearchTerms) {
                                        searchLink = new autoCompleteSearch("SEARCH");
                                        searchLink.search(s);
                                        suggestionsSearchActivated = true;
                                        encodedSearchTerms = true;
                                        searchExpansion();
                                        expandedTopAndBottom = true;
                                    }
                                    else {
                                        searchLink.search(s);
                                        if (!expandedTopAndBottom) {
                                            searchExpansion();
                                            expandedTopAndBottom = true;
                                        }
                                    }
                                }
                            });
                        }
                    }, DELAY);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        cardMid = secondRowLayout.findViewById(R.id.cardMid);

        return secondRowLayout;
    }

    public void searchExpansion() {
        ArrayList<View> listOfV = new ArrayList<>();
        listOfV.add(suggestionOne);
        listOfV.add(suggestionTwo);
        listOfV.add(suggestionThree);
        listOfV.add(suggestionLeft);
        listOfV.add(suggestionCentre);
        listOfV.add(suggestionRight);
        settingsTitleOnHome.setVisibility(View.GONE);
        secondRowScroll.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        fadeInMultiple(listOfV);
        ObjectAnimator scaleCardY = ObjectAnimator.ofFloat(cardMid, "scaleY", 1f, 1.11f);
        scaleCardY.setDuration(150);
        scaleCardY.start();
        expandTopCard("EXPAND");
    }

    public void searchContraction() {
        ArrayList<View> listOfV = new ArrayList<>();
        listOfV.add(suggestionOne);
        listOfV.add(suggestionTwo);
        listOfV.add(suggestionThree);
        listOfV.add(suggestionLeft);
        listOfV.add(suggestionCentre);
        listOfV.add(suggestionRight);
        settingsTitleOnHome.setVisibility(View.VISIBLE);
        secondRowScroll.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
        fadeAwayMultiple(listOfV);
        ObjectAnimator scaleCardY = ObjectAnimator.ofFloat(cardMid, "scaleY", 1f, 1.11f);
        scaleCardY.setInterpolator(new AnimationInterpolator());
        scaleCardY.setDuration(150);
        scaleCardY.start();
        expandTopCard("CONTRACT");
        hideKeyboardFrom(getActivity(),searchEditText );
        expandedTopAndBottom = false;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void businessHighlight() {
        if (!childCardDown) {
            slideSearch(NextChildCardAnimation_For_Animator);
            createTestCard();
            createBusinessOverview();
            slideContainerCard("SLIDE DOWN");
            presentCard = "SETTINGS_BUSINESS";
            NextChildCardAnimation_For_Animator = "SLIDE UP";
            childCardDown = true;
        } else if (childCardDown) {
            if (!presentCard.equals("SETTINGS_BUSINESS")) {
                replaceFragment("SETTINGS_BUSINESS");
                presentCard = "SETTINGS_BUSINESS";
                childCardDown = true;
            }
        }
    }

    public void advertiseHighlight() {
        if (!childCardDown) {
            slideSearch(NextChildCardAnimation_For_Animator);
            createTestCard();
            createAdvertiseOverview();
            slideContainerCard("SLIDE DOWN");
            presentCard = "SETTINGS_ADVERTISE";
            NextChildCardAnimation_For_Animator = "SLIDE UP";
            childCardDown = true;
        } else if (childCardDown) {
            if (!presentCard.equals("SETTINGS_ADVERTISE")) {
                replaceFragment("SETTINGS_ADVERTISE");
                presentCard = "SETTINGS_ADVERTISE";
                childCardDown = true;
            }
        }
    }

    public void atHomeHighlight() {
        if (!childCardDown) {
            slideSearch(NextChildCardAnimation_For_Animator);
            createTestCard();
            createAtHomeOverview();
            slideContainerCard("SLIDE DOWN");
            presentCard = "SETTINGS_ATHOME";
            NextChildCardAnimation_For_Animator = "SLIDE UP";
            childCardDown = true;
        } else if (childCardDown) {
            if (!presentCard.equals("SETTINGS_ATHOME")) {
                replaceFragment("SETTINGS_ATHOME");
                presentCard = "SETTINGS_ATHOME";
                childCardDown = true;
            }
        }
    }

    public void accountHighlight() {
        if (!childCardDown) {
            slideSearch(NextChildCardAnimation_For_Animator);
            createTestCard();
            createAccountOverview();
            slideContainerCard("SLIDE DOWN");
            presentCard = "SETTINGS_ACCOUNT";
            NextChildCardAnimation_For_Animator = "SLIDE UP";
            childCardDown = true;
        } else if (childCardDown) {
            if (!presentCard.equals("SETTINGS_ACCOUNT")) {
                replaceFragment("SETTINGS_ACCOUNT");
                presentCard = "SETTINGS_ACCOUNT";
                childCardDown = true;
            }
        }
    }

    public void dietHighlight() {
        if (!childCardDown) {
            slideSearch(NextChildCardAnimation_For_Animator);
            createTestCard();
            createDietOverview();
            slideContainerCard("SLIDE DOWN");
            presentCard = "SETTINGS_DIET";
            NextChildCardAnimation_For_Animator = "SLIDE UP";
            childCardDown = true;
        } else if (childCardDown) {
            if (!presentCard.equals("SETTINGS_DIET")) {
                replaceFragment("SETTINGS_DIET");
                presentCard = "SETTINGS_DIET";
                childCardDown = true;
            }
        }
    }

    public void bankingHighlight() {
        if (!childCardDown) {
            slideSearch(NextChildCardAnimation_For_Animator);
            createTestCard();
            createBankingOverview();
            slideContainerCard("SLIDE DOWN");
            presentCard = "SETTINGS_BANKING";
            NextChildCardAnimation_For_Animator = "SLIDE UP";
            childCardDown = true;
        } else if (childCardDown) {
            if (!presentCard.equals("SETTINGS_BANKING")) {
                replaceFragment("SETTINGS_BANKING");
                presentCard = "SETTINGS_BANKING";
                childCardDown = true;
            }
        }
    }

    public void aboutHighlight() {
        if (!childCardDown) {
            slideSearch(NextChildCardAnimation_For_Animator);
            createTestCard();
            createAboutOverview();
            slideContainerCard("SLIDE DOWN");
            presentCard = "SETTINGS_ABOUT";
            NextChildCardAnimation_For_Animator = "SLIDE UP";
            childCardDown = true;
        } else if (childCardDown) {
            if (!presentCard.equals("SETTINGS_ABOUT")) {
                replaceFragment("SETTINGS_ABOUT");
                presentCard = "SETTINGS_ABOUT";
                childCardDown = true;
            }
        }
    }

    @Override
    public void onSettingsClickIcon(CharSequence title) {
        if (title.equals("Business")) {
            if (!childCardDown) {
                slideSearch(NextChildCardAnimation_For_Animator);
                createTestCard();
                createBusinessOverview();
                slideContainerCard("SLIDE DOWN");
                presentCard = "SETTINGS_BUSINESS";
                NextChildCardAnimation_For_Animator = "SLIDE UP";
                childCardDown = true;
            } else if (childCardDown) {
                if (presentCard.equals("SETTINGS_BUSINESS")) {
                    slideSearch("SLIDE UP");
                    slideContainerCard("SLIDE UP");
                    removeBlank();
                    presentCard = "DEFAULT";
                    NextChildCardAnimation_For_Animator = "SLIDE DOWN";
                    childCardDown = false;
                    if (ScreenSlidUp) {
                        moveTopRowOnClick(0);
                        slideToTopRow(0);
                        ScreenSlidUp = false;
                    }
                } else if (!presentCard.equals("SETTINGS_BUSINESS")) {
                    replaceFragment("SETTINGS_BUSINESS");
                    presentCard = "SETTINGS_BUSINESS";
                    childCardDown = true;
                }
            }

        } else if (title.equals("Account")) {
            if (!childCardDown) {
                slideSearch(NextChildCardAnimation_For_Animator);
                createTestCard();
                createAdvertiseOverview();
                slideContainerCard("SLIDE DOWN");
                presentCard = "SETTINGS_ADVERTISE";
                NextChildCardAnimation_For_Animator = "SLIDE UP";
                childCardDown = true;
            } else if (childCardDown) {
                if (presentCard.equals("SETTINGS_ADVERTISE")) {
                    slideSearch("SLIDE UP");
                    slideContainerCard("SLIDE UP");
                    removeBlank();
                    if (ScreenSlidUp) {
                        moveTopRowOnClick(0);
                        slideToTopRow(0);
                        ScreenSlidUp = false;
                    }
                    presentCard = "DEFAULT";
                    NextChildCardAnimation_For_Animator = "SLIDE DOWN";
                    childCardDown = false;
                } else if (!presentCard.equals("SETTINGS_ADVERTISE")) {
                    replaceFragment("SETTINGS_ADVERTISE");
                    presentCard = "SETTINGS_ADVERTISE";
                    childCardDown = true;
                }
            }

        } else if (title.equals("Diet Filters")) {
            if (!childCardDown) {
                slideSearch(NextChildCardAnimation_For_Animator);
                createTestCard();
                createAtHomeOverview();
                slideContainerCard("SLIDE DOWN");
                presentCard = "SETTINGS_ATHOME";
                NextChildCardAnimation_For_Animator = "SLIDE UP";
                childCardDown = true;
            } else if (childCardDown) {
                if (presentCard.equals("SETTINGS_ATHOME")) {
                    slideSearch("SLIDE UP");
                    slideContainerCard("SLIDE UP");
                    removeBlank();
                    if (ScreenSlidUp) {
                        moveTopRowOnClick(0);
                        slideToTopRow(0);
                        ScreenSlidUp = false;
                    }
                    presentCard = "DEFAULT";
                    NextChildCardAnimation_For_Animator = "SLIDE DOWN";
                    childCardDown = false;
                } else if (!presentCard.equals("SETTINGS_ATHOME")) {
                    replaceFragment("SETTINGS_ATHOME");
                    presentCard = "SETTINGS_ATHOME";
                    childCardDown = true;
                }
            }

        } else if (title.equals("Payment")) {
            if (!childCardDown) {
                slideSearch(NextChildCardAnimation_For_Animator);
                createTestCard();
                createAccountOverview();
                slideContainerCard("SLIDE DOWN");
                presentCard = "SETTINGS_ACCOUNT";
                NextChildCardAnimation_For_Animator = "SLIDE UP";
                childCardDown = true;
            } else if (childCardDown) {
                if (presentCard.equals("SETTINGS_ACCOUNT")) {
                    slideSearch("SLIDE UP");
                    slideContainerCard("SLIDE UP");
                    removeBlank();
                    if (ScreenSlidUp) {
                        moveTopRowOnClick(0);
                        slideToTopRow(0);
                        ScreenSlidUp = false;
                    }
                    if (accountChildrenShown) {
                        adapter.removeAccount();
                    }
                    presentCard = "DEFAULT";
                    NextChildCardAnimation_For_Animator = "SLIDE DOWN";
                    childCardDown = false;
                } else if (!presentCard.equals("SETTINGS_ACCOUNT")) {
                    replaceFragment("SETTINGS_ACCOUNT");
                    presentCard = "SETTINGS_ACCOUNT";
                    childCardDown = true;
                }
            }

        } else if (title.equals("Help")) {
            if (!childCardDown) {
                slideSearch(NextChildCardAnimation_For_Animator);
                createTestCard();
                createDietOverview();
                slideContainerCard("SLIDE DOWN");
                presentCard = "SETTINGS_DIET";
                NextChildCardAnimation_For_Animator = "SLIDE UP";
                childCardDown = true;
            } else if (childCardDown) {
                if (presentCard.equals("SETTINGS_DIET")) {
                    slideSearch("SLIDE UP");
                    slideContainerCard("SLIDE UP");
                    removeBlank();
                    if (ScreenSlidUp) {
                        moveTopRowOnClick(0);
                        slideToTopRow(0);
                        ScreenSlidUp = false;
                    }
                    presentCard = "DEFAULT";
                    NextChildCardAnimation_For_Animator = "SLIDE DOWN";
                    childCardDown = false;
                } else if (!presentCard.equals("SETTINGS_DIET")) {
                    replaceFragment("SETTINGS_DIET");
                    presentCard = "SETTINGS_DIET";
                    childCardDown = true;
                }
            }

        } else if (title.equals("About")) {
            if (!childCardDown) {
                slideSearch(NextChildCardAnimation_For_Animator);
                createTestCard();
                createBankingOverview();
                slideContainerCard("SLIDE DOWN");
                presentCard = "SETTINGS_BANKING";
                NextChildCardAnimation_For_Animator = "SLIDE UP";
                childCardDown = true;
            } else if (childCardDown) {
                if (presentCard.equals("SETTINGS_BANKING")) {
                    slideSearch("SLIDE UP");
                    slideContainerCard("SLIDE UP");
                    removeBlank();
                    if (ScreenSlidUp) {
                        moveTopRowOnClick(0);
                        slideToTopRow(0);
                        ScreenSlidUp = false;
                    }
                    presentCard = "DEFAULT";
                    NextChildCardAnimation_For_Animator = "SLIDE DOWN";
                    childCardDown = false;
                } else if (!presentCard.equals("SETTINGS_BANKING")) {
                    replaceFragment("SETTINGS_BANKING");
                    presentCard = "SETTINGS_BANKING";
                    childCardDown = true;
                }
            }

        } else if (title.equals("Careers")) {
            if (!childCardDown) {
                slideSearch(NextChildCardAnimation_For_Animator);
                createTestCard();
                createAboutOverview();
                slideContainerCard("SLIDE DOWN");
                presentCard = "SETTINGS_ABOUT";
                NextChildCardAnimation_For_Animator = "SLIDE UP";
                childCardDown = true;
            } else if (childCardDown) {
                if (presentCard.equals("SETTINGS_ABOUT")) {
                    slideSearch("SLIDE UP");
                    slideContainerCard("SLIDE UP");
                    removeBlank();
                    if (ScreenSlidUp) {
                        moveTopRowOnClick(0);
                        slideToTopRow(0);
                        ScreenSlidUp = false;
                    }
                    presentCard = "DEFAULT";
                    NextChildCardAnimation_For_Animator = "SLIDE DOWN";
                    childCardDown = false;
                } else if (!presentCard.equals("SETTINGS_ABOUT")) {
                    replaceFragment("SETTINGS_ABOUT");
                    presentCard = "SETTINGS_ABOUT";
                    childCardDown = true;
                }
            }
        }
    }

    public void slideDownAnimation(int SLIDING_ANIMATION) {
        cardMid = (CardView) getActivity().findViewById(R.id.cardMid);
        searchFunction = (EditText) getActivity().findViewById(R.id.searchEdit);

        ArrayList<View> secondRow = new ArrayList<>();
        secondRow.add(cardMid);
        secondRow.add(searchFunction);

        settingsViewOnMain = getActivity().findViewById(R.id.SettingsTitleMain);
        secondRow.add(settingsViewOnMain);

        switch (SLIDING_ANIMATION) {
            case 0:
                if (childCardDown) {
                    removeBlank();
                    presentCard = "DEFAULT";
                    NextChildCardAnimation_For_Animator = "SLIDE DOWN";
                    childCardDown = false;
                }
                for (int i = 0; i < secondRow.size(); i++) {
                    ObjectAnimator bottomRow = ObjectAnimator.ofFloat(secondRow.get(i), "translationY", 0f, 300f);
                    bottomRow.setDuration(210);
                    Animation fadeOutAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.medium_fade_out);
                    bottomRow.start();
                    secondRow.get(i).startAnimation(fadeOutAnimation);
                    secondRow.get(i).setVisibility(View.INVISIBLE);
                }
                break;
            case 1:
                for (int i = 0; i < secondRow.size(); i++) {
                    ObjectAnimator bottomRow = ObjectAnimator.ofFloat(secondRow.get(i), "translationY", 0f, 500f);
                    bottomRow.setInterpolator(new AnimationInterpolator());
                    bottomRow.setDuration(350);
                    Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.medium_fade_in);
                    bottomRow.start();
                    secondRow.get(i).startAnimation(fadeInAnimation);
                    secondRow.get(i).setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void slideToTopRow(int slidingAnimationStatus) {
        ConstraintLayout constraintTop = (ConstraintLayout) getActivity().findViewById(R.id.constraintHome);
        ObjectAnimator slideToTopRow = ObjectAnimator.ofFloat(constraintTop, "translationY", 0f, -380f);

        switch (slidingAnimationStatus) {
            case 1:
                Log.i("TAG", "up");
                slideToTopRow.setDuration(250);
                slideToTopRow.start();

                break;
            case 0:
                Log.i("TAG", "down");
                slideToTopRow.setInterpolator(new AnimationInterpolator());
                slideToTopRow.setDuration(250);
                slideToTopRow.start();
        }
    }

    public void slideSearch(String SlideUpOrSlideDown) {
        View AutoCompleteSearch = (View) getView().findViewById(R.id.searchEdit);
        ObjectAnimator AutoCompleteSearchAnimator = ObjectAnimator.ofFloat(AutoCompleteSearch, "translationY", 0f, 275f);

        switch (SlideUpOrSlideDown) {
            case "SLIDE DOWN":
                AutoCompleteSearchAnimator.setDuration(100);
                AutoCompleteSearchAnimator.start();
                Animation fadeOutAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.quick_fade_out);
                AutoCompleteSearch.startAnimation(fadeOutAnimation);
                AutoCompleteSearch.setVisibility(View.INVISIBLE);
                break;

            case "SLIDE UP":
                AutoCompleteSearchAnimator.setInterpolator(new AnimationInterpolator());
                AutoCompleteSearchAnimator.setDuration(250);
                AutoCompleteSearchAnimator.start();
                Animation fadeInAniamtion = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.quick_fade_in);
                AutoCompleteSearch.startAnimation(fadeInAniamtion);
                AutoCompleteSearch.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void slideContainerCard(String SlideUpOrSlideDown) {
        View cardView = (View) getView().findViewById(R.id.second_row_child);
        ObjectAnimator cardAnimator = ObjectAnimator.ofFloat(cardView, "translationY", 30f, 270f);

        switch (SlideUpOrSlideDown) {
            case "SLIDE DOWN":
                cardAnimator.setDuration(250);
                cardAnimator.start();
                Animation fadeOutAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.medium_fade_in);
                cardView.startAnimation(fadeOutAnimation);
                cardView.setVisibility(View.VISIBLE);
                childCardDown = true;
                break;

            case "SLIDE UP":
                cardAnimator.setInterpolator(new AnimationInterpolator());
                cardAnimator.setDuration(250);
                cardAnimator.start();
                Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.medium_fade_out);
                cardView.startAnimation(fadeInAnimation);
                removeChildFragment();
                childCardDown = false;
                break;
        }
    }

    public void createTestCard() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        blank_card_fragment blank_card_fragment = new blank_card_fragment();
        childFragTrans.add(R.id.second_row_child, blank_card_fragment, "TESTCARD");
        childFragTrans.commit();
    }

    public void createAboutOverview() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_aboutOverview_fragment settingsAboutFragment = new settings_aboutOverview_fragment();
        childFragTrans.add(R.id.second_row_child, settingsAboutFragment, "SETTINGS_ABOUT");
        childFragTrans.commit();
    }

    public void createAccountOverview() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_accountOverview_fragment settingsAccountFragment = new settings_accountOverview_fragment();
        childFragTrans.add(R.id.second_row_child, settingsAccountFragment, "SETTINGS_ACCOUNT");
        childFragTrans.commit();
    }

    public void createAdvertiseOverview() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_advertiseOverview_fragment settingsAdvertiseFragment = new settings_advertiseOverview_fragment();
        childFragTrans.add(R.id.second_row_child, settingsAdvertiseFragment, "SETTINGS_ADVERTISE");
        childFragTrans.commit();
    }

    public void createAtHomeOverview() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_atHomeOverview_fragment settingsAtHomeFragment = new settings_atHomeOverview_fragment();
        childFragTrans.add(R.id.second_row_child, settingsAtHomeFragment, "SETTINGS_ATHOME");
        childFragTrans.commit();
    }

    public void createBankingOverview() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_bankingOverview_fragment settingsBankingFragment = new settings_bankingOverview_fragment();
        childFragTrans.add(R.id.second_row_child, settingsBankingFragment, "SETTINGS_BANKING");
        childFragTrans.commit();
    }

    public void createBusinessOverview() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_businessOverview_fragment settingsBusinessFragment = new settings_businessOverview_fragment();
        childFragTrans.add(R.id.second_row_child, settingsBusinessFragment, "SETTINGS_BUSINESS");
        childFragTrans.commit();
    }

    public void createDietOverview() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_dietOverview_fragment settingsDietFragment = new settings_dietOverview_fragment();
        childFragTrans.add(R.id.second_row_child, settingsDietFragment, "SETTINGS_DIET");
        childFragTrans.commit();
    }

    public void createSettingsGeneralAccount() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_general_account mSettingsGeneral = new settings_general_account();
        childFragTrans.add(R.id.second_row_child, mSettingsGeneral, "GENERAL_ACCOUNT");
        childFragTrans.commit();
    }

    public void removeChildFragment() {
        Fragment deleteFragment = getChildFragmentManager().findFragmentByTag(presentCard);
        if (deleteFragment != null) {
            getChildFragmentManager().beginTransaction().remove(deleteFragment).commit();
        }
    }

    public void removeBlank() {
        Fragment deleteFragment = getChildFragmentManager().findFragmentByTag("TESTCARD");
        if (deleteFragment != null) {
            getChildFragmentManager().beginTransaction().remove(deleteFragment).commit();
        }
    }

    public void replaceFragment(String newCard) {
        if (childCardDown) {
            switch(newCard) {
                case "TESTCARD":
                    removeChildFragment();
                    createTestCard();
                    break;
                case "SETTINGS_ABOUT":
                    removeChildFragment();
                    createAboutOverview();
                    break;
                case "SETTINGS_ACCOUNT":
                    removeChildFragment();
                    createAccountOverview();
                    break;
                case "SETTINGS_ADVERTISE":
                    removeChildFragment();
                    createAdvertiseOverview();
                    break;
                case "SETTINGS_ATHOME":
                    removeChildFragment();
                    createAtHomeOverview();
                    break;
                case "SETTINGS_BANKING":
                    removeChildFragment();
                    createBankingOverview();
                    break;
                case "SETTINGS_BUSINESS":
                    removeChildFragment();
                    createBusinessOverview();
                    break;
                case "SETTINGS_DIET":
                    removeChildFragment();
                    createDietOverview();
                    break;
                case "SETTINGS_ACCOUNT_GENERAL":
                    removeChildFragment();
                    createSettingsGeneralAccount();
                    break;
            }
        }
    }

    public void slideScreenUp() {

        if (ScreenSlidUp) {
            moveTopRowOnClick(0);
            slideToTopRow(0);
            ScreenSlidUp = false;
        }
        else {
            moveTopRowOnClick(1);
            slideToTopRow(1);
            ScreenSlidUp = true;
        }
    }

    public interface slideTopRow {
        void moveUpTopRow(int slidingAnimationType);
        void expandTopRow(String type);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        if (context instanceof Activity) {
            a = (Activity) context;
            try {
                mSlideTopRow = (settings_Home_SecondRow_fragment.slideTopRow) a;
                mExpandTopRow = (settings_Home_SecondRow_fragment.slideTopRow) a;
                mRegister = (settings_Home_SecondRow_fragment.registerTopRow) a;
                mOpenVenueRego = (settings_Home_SecondRow_fragment.openVenueRegistration) a;
            }
            catch (ClassCastException e) {
                throw new ClassCastException(a.toString() + " must implement SlideTopRow Interface");
            }
        }
    }

    @Override
    public void onDetach() {
        mSlideTopRow = null;
        mExpandTopRow = null;
        mRegister = null;
        mOpenVenueRego = null;
        super.onDetach();
    }

    public void moveTopRowOnClick(int slidingAnimationStatus) {
        mSlideTopRow.moveUpTopRow(slidingAnimationStatus);
    }

    public interface registerTopRow {
        void register(String newRegistration);
    }

    public void registration(String registerType) {
        mRegister.register(registerType);
    }

    public void expandTopCard(String type) {
        mExpandTopRow.expandTopRow(type);
    }

    public void addTitle(String titleToAdd) {
        settingsTitlesForAdapter.addTitle(titleToAdd);
    }

    public void changeAdapterForTitle(String newAdapter) {

        //ADAPTER captures per input - ability to delete.

        switch (newAdapter) {
            case "KEYWORDS":
                updateDataAdapter("CREATE_AD", "KEYWORDS");
                break;
            case "ADVERTISE":
                break;
        }
    }

    public void updateDataAdapter(String adapter, String newData) {
        switch (adapter) {
            case "CREATE_AD":
                if (!createAdAdapter) {
                    ArrayList<String> advertise = new ArrayList<>();
                    advertise.add("Adverts");
                    ArrayList<Integer> photos = new ArrayList<>();
                    photos.add(R.drawable.square_orange);
                    advertiseAdapter = new advertiseAdapter(advertise, photos);
                    recyclerView.setAdapter(advertiseAdapter);
                    createAdAdapter = true;
                }
        }
    }

    public void updateAdapter(boolean openAccountChild) {
        if (openAccountChild) {
            adapter.updateAccount();
            accountChildrenShown = true;
        }

    }

    public void updatePresentCard(String card) {
        presentCard = card;
    }

    public interface openVenueRegistration {
        void openVenueRego(String type);
    }

    public void openVRego(String type) {
        Log.i("TAG", "Present card; " + presentCard);
        clearSecond();
        mOpenVenueRego.openVenueRego(type);
    }

    public void clearSecond() {
        Fragment deleteFragment = getChildFragmentManager().findFragmentByTag(presentCard);
        if (deleteFragment != null) {
            getChildFragmentManager().beginTransaction().remove(deleteFragment).commit();
        }
    }

    public void fadeInMultiple(ArrayList<View> listOfViews) {
        for (int i = 0; i < listOfViews.size(); i++) {
            Log.i("TAG", "Size: " + listOfViews.size());
            listOfViews.get(i).setVisibility(View.VISIBLE);
            ObjectAnimator fadeInAnimation = ObjectAnimator.ofFloat(listOfViews.get(i), "alpha", 0f, 1f);
            fadeInAnimation.setDuration(250);
            fadeInAnimation.start();
        }
    }

    public void fadeAwayMultiple(ArrayList<View> listOfViews) {
        for (int i = 0; i < listOfViews.size(); i++) {
            ObjectAnimator fadeAwayAnimation = ObjectAnimator.ofFloat(listOfViews.get(i), "alpha", 1f, 0f);
            fadeAwayAnimation.setDuration(250);
            fadeAwayAnimation.start();
            listOfViews.get(i).setVisibility(View.GONE);
        }
    }

}
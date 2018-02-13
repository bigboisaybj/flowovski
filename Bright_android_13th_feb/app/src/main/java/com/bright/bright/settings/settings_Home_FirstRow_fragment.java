package com.bright.bright.settings;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextView;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


import java.util.ArrayList;
import com.bright.bright.R;

public class settings_Home_FirstRow_fragment extends Fragment implements settings_AtHomeAdapter.ListAdapterListener, settings_VenueAdapter.VenueAdapterInterface {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter venueAdapter;
    private RecyclerView.Adapter registrationAdapter;
    private ArrayList<String> titleAtHomeDataSet;
    private ArrayList<String> titleVenueDataSet;
    private ArrayList<String> titleRegistrationDataSet;
    private ArrayList<Integer> imageAtHomeDataSet;
    private ArrayList<Integer> imageVenueDataSet;
    private ArrayList<Integer> imageRegistrationDataSet;

    userData venueUserDataSet;
    userData atHomeUserDataSet;
    onTopLeftClicked mCallback;
    View topLayout;
    String presentHightlight = "At_Home";
    boolean At_Home_AlreadyHighlighted = true;
    String NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
    String presentCard = "DEFAULT";
    Boolean childCardDown = false;
    Boolean registeringVenue = false;
    Boolean venueRegistrationCompleted = false;
    Boolean registrationTitleAdded = false;
    Boolean atHomeRegistrationCompleted = false;
    String venueTitle;
    TextView At_HomeText;
    TextView VenueText;
    TextView creatingMerchantAccount;
    View atHomeText;
    View businessText;
    View registrationTitle;
    ScrollView scrollView;
    View firstRowContainer;
    View cardTop;
    float reduceAlpha = 1.0f;
    float addAlpha = 0.5f;
    float registrationAlpha = 1f;
    float atHomeAlpha = 0.6f;
    float venueAlpha = 0.3f;
    int overallYScroll = 0;
    float presentScroll = 0;

    public String [] titleATHOME = {"Facebook login"};
    public String [] titleVENUE = {"Business", "Address", "Menu", "Test1"};
    public String [] titleREGISTRATION = {"Continue", "Edit", "Message", "Test1"};
    public int[] imagesATHOME = {R.drawable.square_blue};
    public int[] imagesVENUE = {R.drawable.square_orange, R.drawable.square_blue, R.drawable.square_grey, R.drawable.square_lightblue};
    public int[] imagesREGISTRATION = {R.drawable.square_grey, R.drawable.square_lightblue, R.drawable.square_orange, R.drawable.square_lightblue};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        topLayout = inflater.inflate(R.layout.settings_home_scrollable_firstrow, container, false);

        venueUserDataSet = new userData();
        atHomeUserDataSet = new userData();

        titleAtHomeDataSet = new ArrayList<>();
        for (int i = 0; i < titleATHOME.length; i++) {
            titleAtHomeDataSet.add(titleATHOME[i]);
        }

        titleVenueDataSet = new ArrayList<>();
        for (int i = 0; i < titleVENUE.length; i++) {
            titleVenueDataSet.add(titleVENUE[i]);
        }

        titleRegistrationDataSet = new ArrayList<>();
        for (int i = 0; i < titleREGISTRATION.length; i++) {
            titleRegistrationDataSet.add(titleREGISTRATION[i]);
        }

        imageAtHomeDataSet = new ArrayList<>();
        for (int i = 0; i < imagesATHOME.length; i++) {
            imageAtHomeDataSet.add(imagesATHOME[i]);
        }

        imageVenueDataSet = new ArrayList<>();
        for (int i = 0; i < imagesVENUE.length; i++) {
            imageVenueDataSet.add(imagesVENUE[i]);
        }

        imageRegistrationDataSet = new ArrayList<>();
        for (int i = 0; i < imagesREGISTRATION.length; i++) {
            imageRegistrationDataSet.add(imagesREGISTRATION[i]);
        }

        firstRowContainer = getActivity().findViewById(R.id.Above_top_row_fragment_container);
        cardTop = topLayout.findViewById(R.id.cardTop);

        recyclerView = (RecyclerView) topLayout.findViewById(R.id.recycler_view_SecondRow);
        layoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        scrollView = (ScrollView) topLayout.findViewById(R.id.settingsFirstRowScrollView);
        creatingMerchantAccount = (TextView) getActivity().findViewById(R.id.Create_Merchant_Account);

        adapter = new settings_AtHomeAdapter(titleAtHomeDataSet,imageAtHomeDataSet,this);
        venueAdapter = new settings_VenueAdapter(titleVenueDataSet,imageVenueDataSet,this);
        registrationAdapter = new settings_VenueAdapter(titleRegistrationDataSet, imageRegistrationDataSet, this);
        recyclerView.setAdapter(adapter);

        At_HomeText = (TextView) topLayout.findViewById(R.id.Registration_At_Home);
        At_HomeText.setMovementMethod(new ScrollingMovementMethod());

        VenueText = (TextView) topLayout.findViewById(R.id.Registration_Venue);

        atHomeText = (View) topLayout.findViewById(R.id.Registration_At_Home);
        businessText = (View) topLayout.findViewById(R.id.Registration_Venue);
        businessText.setAlpha(0.5f);

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                registrationTitle = topLayout.findViewById(R.id.registrationTitle);

                if (registrationTitle.getVisibility() == View.GONE) {
                    if (scrollY > 98)  {
                        return;
                    }
                    if (reduceAlpha >= 1f) {
                        reduceAlpha = 1f;
                    }
                    if (addAlpha >= 1f) {
                        addAlpha = 1f;
                    }

                    if (scrollY > oldScrollY) {
                        businessHighlight();
                        reduceAlpha -= 0.1f;
                        addAlpha += 0.1f;
                        if (reduceAlpha >= 0) {
                            if (reduceAlpha <= 0.1f) {
                                reduceAlpha = 0.1f;
                            }
                            if (scrollY >= 50) {
                                businessText.setAlpha(1);
                            }
                            if (addAlpha <= 0.1f) {
                                addAlpha = 0.1f;
                            }
                            atHomeText.setAlpha(reduceAlpha);
                            businessText.setAlpha(addAlpha);
                        }
                        presentScroll = overallYScroll;
                    }

                    if (scrollY < oldScrollY) {
                        atHomeHighlight();
                        reduceAlpha += 0.1f;
                        addAlpha -= 0.1f;
                        if (reduceAlpha >= 0){
                            if (reduceAlpha <= 0.1f) {
                                reduceAlpha = 0.1f;
                            }
                            if (scrollY <= 5) {
                                atHomeText.setAlpha(1);
                            }
                            if (addAlpha <= 0.1f) {
                                addAlpha = 0.1f;
                            }
                            atHomeText.setAlpha(reduceAlpha);
                            businessText.setAlpha(addAlpha);
                        }
                        presentScroll = overallYScroll;
                    }
                }

                else {
                    registeringVenue = true;

                    if (registrationAlpha >= 1f) {
                        registrationAlpha = 1f;
                    }
                    if (atHomeAlpha >= 1f) {
                        atHomeAlpha = 1f;
                    }
                    if (venueAlpha >= 1f) {
                        venueAlpha = 1f;
                    }

                    if (registrationAlpha <= 0.1f) {
                        registrationAlpha = 0.1f;
                    }
                    if (atHomeAlpha <= 0.1f) {
                        atHomeAlpha = 0.1f;
                    }
                    if (venueAlpha <= 0.1f) {
                        venueAlpha = 0.1f;
                    }


                    if (scrollY > oldScrollY) {
                            if (scrollY >= 0 && scrollY < 75) {
                                registrationAlpha -= 0.1f;
                                atHomeAlpha += 0.1f;
                                if (registrationAlpha <= 0.1f) {
                                    registrationAlpha = 0.1f;
                                }
                                registrationTitle.setAlpha(registrationAlpha);
                                atHomeText.setAlpha(atHomeAlpha);
                            }

                            if (scrollY >= 76 && scrollY < 105) {
                                atHomeHighlight();
                                atHomeAlpha += 0.01f;
                                venueAlpha += 0.1f;
                                registrationAlpha -= 0.01f;
                                if (registrationAlpha <= 0.1f) {
                                    registrationAlpha = 0.1f;
                                }
                                atHomeText.setAlpha(atHomeAlpha);
                                businessText.setAlpha(venueAlpha);
                                registrationTitle.setAlpha(registrationAlpha);
                            }

                            if (scrollY >= 106 && scrollY < 176) {
                                businessHighlight();
                                atHomeAlpha -= 0.1f;
                                venueAlpha += 0.1f;
                                if (atHomeAlpha <= 0.1f) {
                                    atHomeAlpha = 0.1f;
                                }
                                atHomeText.setAlpha(atHomeAlpha);
                                businessText.setAlpha(venueAlpha);
                            }
                    }
                    if (scrollY < oldScrollY) {
                            if (scrollY >= 0 && scrollY < 75) {
                                registrationHighlight();
                                atHomeAlpha -= 0.05f;
                                registrationAlpha += 0.1f;
                                if (atHomeAlpha <= 0.1f) {
                                    atHomeAlpha = 0.1f;
                                }
                                registrationTitle.setAlpha(registrationAlpha);
                                atHomeText.setAlpha(atHomeAlpha);
                            }

                            if (scrollY >= 76 && scrollY < 105) {
                                atHomeHighlight();
                                atHomeAlpha -= 0.01f;
                                venueAlpha -= 0.1f;
                                registrationAlpha += 0.01f;

                                if (venueAlpha <= 0.1f) {
                                    venueAlpha = 0.1f;
                                }
                                atHomeText.setAlpha(atHomeAlpha);
                                businessText.setAlpha(venueAlpha);
                                registrationTitle.setAlpha(registrationAlpha);
                            }

                            if (scrollY >= 106 && scrollY < 176) {
                                businessHighlight();
                                atHomeAlpha += 0.05f;
                                venueAlpha -= 0.1f;
                                if (venueAlpha <= 0.1f) {
                                    venueAlpha = 0.1f;
                                }
                                atHomeText.setAlpha(atHomeAlpha);
                                businessText.setAlpha(venueAlpha);
                            }
                    }
                }
            }
        });
        return topLayout;
    }

    public void atHomeHighlight() {
        if (presentHightlight.equals("At_Home")) {
            //expandCard();
        }

        else if (!presentHightlight.equals("At_Home")) {
            At_Home_RecyclerViewVisibility(1);
            if (childCardDown) {
                Log.i("TAG", "Activated");
                moveSecondRowAfterClick(1);
                slideCard("SLIDE UP");
                presentCard = "DEFAULT";
                NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                childCardDown = false;
            }
            //titlesClick("At_Home");
            presentHightlight = "At_Home";
        }
    }

    public void businessHighlight() {
        if (presentHightlight.equals("Venue")) {
            //expandCard();
        }

        else if (!presentHightlight.equals("Venue")) {
            At_Home_RecyclerViewVisibility(2);
            if (childCardDown) {
                moveSecondRowAfterClick(1);
                slideCard("SLIDE UP");
                presentCard = "DEFAULT";
                NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                childCardDown = false;
            }
            //titlesClick("Venue");
            presentHightlight = "Venue";
        }
    }

    public void registrationHighlight() {
        if (presentHightlight.equals("Register")) {
            //expandCard();
        }

        else if (!presentHightlight.equals("Register")) {
            At_Home_RecyclerViewVisibility(3);
            if (childCardDown) {
                moveSecondRowAfterClick(1);
                slideCard("SLIDE UP");
                presentCard = "DEFAULT";
                NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                childCardDown = false;
            }
            //titlesClick("Venue");
            presentHightlight = "Register";
        }


    }

    @Override
    public void onClickButton(int position) {


        //TODO: NEED TO ADD WHETHER TO CONTINUE REGISTERING OR NOT


        switch (position) {
            case 0:
                if (!childCardDown && !atHomeRegistrationCompleted) {
                    moveSecondRowAfterClick(0);
                    createFBLoginCard();
                    slideCard("SLIDE DOWN");
                    presentCard = "XPFragment";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                }
                else if (childCardDown && !atHomeRegistrationCompleted) {
                    if (presentCard.equals("XPFragment")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    }
                    else if (!presentCard.equals("XPFragment")) {
                        replaceFragment("XPFragment");
                        presentCard = "XPFragment";
                        childCardDown = true;
                    }
                }
                else if (!childCardDown && atHomeRegistrationCompleted) {
                    moveSecondRowAfterClick(0);
                    createContinueRegisteringCardAtHome();
                    slideCard("SLIDE DOWN");
                    presentCard = "CONTINUE_REGISTERING_ATHOME";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && atHomeRegistrationCompleted) {
                    if (presentCard.equals("CONTINUE_REGISTERING_ATHOME")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("CONTINUE_REGISTERING_ATHOME")) {
                        replaceFragment("CONTINUE_REGISTERING_ATHOME");
                        presentCard = "CONTINUE_REGISTERING_ATHOME";
                        childCardDown = true;
                    }
                }

                break;
            case 1:
                if (!childCardDown && !atHomeRegistrationCompleted) {
                    moveSecondRowAfterClick(0);
                    createAddressCard();
                    slideCard("SLIDE DOWN");
                    presentCard = "addressFragment";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                }
                else if (childCardDown && !atHomeRegistrationCompleted) {
                    if (presentCard.equals("addressFragment")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    }
                    else if (!presentCard.equals("addressFragment")) {
                        replaceFragment("addressFragment");
                        presentCard = "addressFragment";
                        childCardDown = true;
                    }
                }
                else if (!childCardDown && atHomeRegistrationCompleted) {
                    Log.i("TAG", "this");
                    moveSecondRowAfterClick(0);
                    createContinueRegisteringCardAtHome();
                    slideCard("SLIDE DOWN");
                    presentCard = "CONTINUE_REGISTERING_ATHOME";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && atHomeRegistrationCompleted) {
                    if (presentCard.equals("CONTINUE_REGISTERING_ATHOME")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("CONTINUE_REGISTERING_ATHOME")) {
                        replaceFragment("CONTINUE_REGISTERING_ATHOME");
                        presentCard = "CONTINUE_REGISTERING_ATHOME";
                        childCardDown = true;
                    }
                }
                break;
            case 2:
                if (!childCardDown && !atHomeRegistrationCompleted) {
                    moveSecondRowAfterClick(0);
                    createPhotosCard();
                    slideCard("SLIDE DOWN");
                    presentCard = "photosFragment";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                }
                else if (childCardDown && !atHomeRegistrationCompleted) {
                    if (presentCard.equals("photosFragment")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    }
                    else if (!presentCard.equals("photosFragment")) {
                        replaceFragment("photosFragment");
                        presentCard = "photosFragment";
                        childCardDown = true;

                    }
                }
                else if (!childCardDown && atHomeRegistrationCompleted) {
                    moveSecondRowAfterClick(0);
                    createContinueRegisteringCardAtHome();
                    slideCard("SLIDE DOWN");
                    presentCard = "CONTINUE_REGISTERING_ATHOME";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && atHomeRegistrationCompleted) {
                    if (presentCard.equals("CONTINUE_REGISTERING_ATHOME")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("CONTINUE_REGISTERING_ATHOME")) {
                        replaceFragment("CONTINUE_REGISTERING_ATHOME");
                        presentCard = "CONTINUE_REGISTERING_ATHOME";
                        childCardDown = true;
                    }
                }
                break;
        }

    }
    @Override
    public void onVenueButtonClick(int position) {
        switch (position) {
            case 0:
                if (!childCardDown && !registeringVenue) {
                    moveSecondRowAfterClick(0);
                    createBusinessCard();
                    slideCard("SLIDE DOWN");
                    presentCard = "BUSINESSCARD";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && !registeringVenue) {
                    if (presentCard.equals("BUSINESSCARD")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("BUSINESSCARD")) {
                        replaceFragment("BUSINESSCARD");
                        presentCard = "BUSINESSCARD";
                        childCardDown = true;
                    }
                }
                else if (!childCardDown && registeringVenue) {
                    moveSecondRowAfterClick(0);
                    createContinueRegisteringCardVenue();
                    slideCard("SLIDE DOWN");
                    presentCard = "CONTINUE_REGISTERING";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && registeringVenue) {
                    if (presentCard.equals("CONTINUE_REGISTERING")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("CONTINUE_REGISTERING")) {
                        replaceFragment("CONTINUE_REGISTERING");
                        presentCard = "CONTINUE_REGISTERING";
                        childCardDown = true;
                    }
                }
                break;
            case 1:
                if (!childCardDown && !registeringVenue) {
                    moveSecondRowAfterClick(0);
                    createBusinessAddressCard();
                    slideCard("SLIDE DOWN");
                    presentCard = "BUSINESSADDRESS";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && !registeringVenue) {
                    if (presentCard.equals("BUSINESSADDRESS")) {
                        moveSecondRowAfterClick(1);
                        slideCardVenue("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("BUSINESSADDRESS")) {
                        replaceFragment("BUSINESSADDRESS");
                        presentCard = "BUSINESSADDRESS";
                        childCardDown = true;
                    }
                }
                else if (!childCardDown && registeringVenue) {
                    moveSecondRowAfterClick(0);
                    createContinueRegisteringCardVenue();
                    slideCard("SLIDE DOWN");
                    presentCard = "CONTINUE_REGISTERING";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && registeringVenue) {
                    if (presentCard.equals("CONTINUE_REGISTERING")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("CONTINUE_REGISTERING")) {
                        replaceFragment("CONTINUE_REGISTERING");
                        presentCard = "CONTINUE_REGISTERING";
                        childCardDown = true;
                    }
                }
                break;
            case 2:
                if (!childCardDown && !registeringVenue) {
                    moveSecondRowAfterClick(0);
                    createMenuCard();
                    slideCard("SLIDE DOWN");
                    presentCard = "MENU";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && !registeringVenue) {
                    if (presentCard.equals("MENU")) {
                        moveSecondRowAfterClick(1);
                        slideCardVenue("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("MENU")) {
                        replaceFragment("MENU");
                        presentCard = "MENU";
                        childCardDown = true;
                    }
                }
                else if (!childCardDown && registeringVenue) {
                    moveSecondRowAfterClick(0);
                    createMenuCard();
                    slideCard("SLIDE DOWN");
                    presentCard = "CONTINUE_REGISTERING";
                    NextAddressCardAnimation_For_Animator = "SLIDE UP";
                    childCardDown = true;
                    break;
                } else if (childCardDown && registeringVenue) {
                    if (presentCard.equals("CONTINUE_REGISTERING")) {
                        moveSecondRowAfterClick(1);
                        slideCard("SLIDE UP");
                        presentCard = "DEFAULT";
                        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
                        childCardDown = false;
                        break;
                    } else if (!presentCard.equals("CONTINUE_REGISTERING")) {
                        replaceFragment("CONTINUE_REGISTERING");
                        presentCard = "CONTINUE_REGISTERING";
                        childCardDown = true;
                    }
                }
                break;
        }
    }

    public void createContinueRegisteringCardVenue() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        business_rego_continueRegistering_fragment mContinueFragment = new business_rego_continueRegistering_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, mContinueFragment, "CONTINUE_REGISTERING");
        childFragTrans.commit();
    }

    public void createContinueRegisteringCardAtHome() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        atHome_continueRegistering_fragment mContinueFragment = new atHome_continueRegistering_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, mContinueFragment, "CONTINUE_REGISTERING_ATHOME");
        childFragTrans.commit();
    }

    public boolean atHomeRegoStatus() {
        return atHomeRegistrationCompleted;
    }

    private void titlesClick(String AnimationType) {
        TextView At_Home_TextView = (TextView) topLayout.findViewById(R.id.Registration_At_Home);
        ObjectAnimator scaleAnimation_ATHOME_X_HIGHLIGHT = ObjectAnimator.ofFloat(At_Home_TextView, "scaleX", 0.8f, 1f);
        ObjectAnimator scaleAnimation_ATHOME_Y_HIGHLIGHT = ObjectAnimator.ofFloat(At_Home_TextView, "scaleY", 0.8f, 1f);


        ObjectAnimator scaleAnimation_ATHOME_X_UNHIGHLIGHT = ObjectAnimator.ofFloat(At_Home_TextView, "scaleX", 1f, 0.8f);
        ObjectAnimator scaleAnimation_ATHOME_Y_UNHIGHLIGHT = ObjectAnimator.ofFloat(At_Home_TextView, "scaleY", 1f, 0.8f);

        TextView Venue_TextView = (TextView) topLayout.findViewById(R.id.Registration_Venue);
        ObjectAnimator scaleAnimation_VENUE_X_HIGHLIGHT = ObjectAnimator.ofFloat(Venue_TextView, "scaleX", 1f, 1.3f);
        ObjectAnimator scaleAnimation_VENUE_Y_HIGHLIGHT = ObjectAnimator.ofFloat(Venue_TextView, "scaleY", 1f, 1.3f);

        ObjectAnimator scaleAnimation_VENUE_X_UNHIGHLIGHT = ObjectAnimator.ofFloat(Venue_TextView, "scaleX", 1.3f, 1f);
        ObjectAnimator scaleAnimation_VENUE_Y_UNHIGHLIGHT = ObjectAnimator.ofFloat(Venue_TextView, "scaleY", 1.3f, 1f);

        if (AnimationType.equals("At_Home")) {
            scaleAnimation_ATHOME_X_HIGHLIGHT.setDuration(100);
            scaleAnimation_ATHOME_Y_HIGHLIGHT.setDuration(100);
            scaleAnimation_VENUE_X_UNHIGHLIGHT.setDuration(100);
            scaleAnimation_VENUE_Y_UNHIGHLIGHT.setDuration(100);
            scaleAnimation_ATHOME_X_HIGHLIGHT.start();
            scaleAnimation_ATHOME_Y_HIGHLIGHT.start();
            scaleAnimation_VENUE_X_UNHIGHLIGHT.start();
            scaleAnimation_VENUE_Y_UNHIGHLIGHT.start();
        }
        else if (AnimationType.equals("Venue")) {
            if (At_Home_AlreadyHighlighted) {
                scaleAnimation_ATHOME_X_UNHIGHLIGHT.setDuration(100);
                scaleAnimation_ATHOME_Y_UNHIGHLIGHT.setDuration(100);
                scaleAnimation_VENUE_X_HIGHLIGHT.setDuration(100);
                scaleAnimation_VENUE_Y_HIGHLIGHT.setDuration(100);
                scaleAnimation_VENUE_X_HIGHLIGHT.start();
                scaleAnimation_VENUE_Y_HIGHLIGHT.start();
                scaleAnimation_ATHOME_X_UNHIGHLIGHT.start();
                scaleAnimation_ATHOME_Y_UNHIGHLIGHT.start();
                At_Home_AlreadyHighlighted = false;
            }
            else {
                scaleAnimation_VENUE_X_HIGHLIGHT.setDuration(100);
                scaleAnimation_VENUE_Y_HIGHLIGHT.setDuration(100);
                scaleAnimation_ATHOME_X_HIGHLIGHT.setInterpolator(new AnimationInterpolator());
                scaleAnimation_ATHOME_Y_HIGHLIGHT.setInterpolator(new AnimationInterpolator());
                scaleAnimation_ATHOME_X_HIGHLIGHT.setDuration(100);
                scaleAnimation_ATHOME_Y_HIGHLIGHT.setDuration(100);
                scaleAnimation_VENUE_X_HIGHLIGHT.start();
                scaleAnimation_VENUE_Y_HIGHLIGHT.start();
                scaleAnimation_ATHOME_X_HIGHLIGHT.start();
                scaleAnimation_ATHOME_Y_HIGHLIGHT.start();
            }
        }

    }

    public void At_Home_RecyclerViewVisibility(int show) {
        RecyclerView At_Home_OR_Venue = (RecyclerView) topLayout.findViewById(R.id.recycler_view_SecondRow);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.recyclerview_fade_in);

        if (show == 1) {
            At_Home_OR_Venue.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(adapter);
            At_Home_OR_Venue.startAnimation(fadeInAnimation);
            At_Home_OR_Venue.setVisibility(View.VISIBLE);
            atHomeText.setAlpha(1f);
        }
        if (show == 2) {
            At_Home_OR_Venue.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(venueAdapter);
            At_Home_OR_Venue.startAnimation(fadeInAnimation);
            At_Home_OR_Venue.setVisibility(View.VISIBLE);
            businessText.setAlpha(1f);
        }
        if (show == 3) {
            At_Home_OR_Venue.setVisibility(View.INVISIBLE);
            recyclerView.setAdapter(registrationAdapter);
            At_Home_OR_Venue.startAnimation(fadeInAnimation);
            At_Home_OR_Venue.setVisibility(View.VISIBLE);
            registrationTitle.setAlpha(1f);
        }
    }

    public interface onTopLeftClicked {
        void moveSecondRow(int slidingAnimationStatus);
        //void secondRowToTopRow(int slidingAnimationStatus);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity a;
        if (context instanceof Activity) {
            a = (Activity) context;
            try {
                mCallback = (onTopLeftClicked) a;
                //mSecondToTop = (onTopLeftClicked) a;
            }
            catch (ClassCastException e) {
                throw new ClassCastException(a.toString() + " must implement onTopLeftClicked Interface");
            }
        }
    }

    @Override
    public void onDetach() {
        mCallback = null;
        //mSecondToTop = null;
        super.onDetach();
    }

    public void moveSecondRowAfterClick(int slidingAnimationStatus) {
        mCallback.moveSecondRow(slidingAnimationStatus);
    }

    public void createBusinessCard() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        business_rego_venueName_fragment businessCard_fragment = new business_rego_venueName_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, businessCard_fragment, "BUSINESSCARD");
        childFragTrans.commit();
    }

    public void createFBLoginCard(){
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        settings_facebookLogin_fragment facebookLoginCard = new settings_facebookLogin_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, facebookLoginCard, "XPFragment");
        childFragTrans.commit();
    }

    public void createXPCard() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        atHome_XP_fragment xpCard = new atHome_XP_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, xpCard, "XPFragment");
        childFragTrans.commit();
    }

    public void createAddressCard() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        atHome_address_fragment addressCard = new atHome_address_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, addressCard, "addressFragment");
        childFragTrans.commit();
    }

    public void createPhotosCard() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        atHome_photosRegister_fragment photosFrag = new atHome_photosRegister_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, photosFrag, "photosFragment");
        childFragTrans.commit();
    }

    public void createBusinessAddressCard() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        busines_rego_addressCard_fragment mBusinessAddress = new busines_rego_addressCard_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, mBusinessAddress, "BUSINESSADDRESS");
        childFragTrans.commit();
    }

    public void createMenuCard() {
        FragmentManager childFrag = getChildFragmentManager();
        FragmentTransaction childFragTrans = childFrag.beginTransaction();
        business_rego_menuCard_fragment mMenu = new business_rego_menuCard_fragment();
        childFragTrans.add(R.id.bottom_row_fragment_container, mMenu, "MENU");
        childFragTrans.commit();
    }

    public void slideCardVenue(String SlideUpOrSlideDown) {
        View cardView = (View) getView().findViewById(R.id.bottom_row_fragment_container);
        ObjectAnimator cardAnimator = ObjectAnimator.ofFloat(cardView, "translationY", 30f, 270f);

        switch (SlideUpOrSlideDown) {
            case "SLIDE DOWN":
                cardAnimator.setDuration(250);
                cardAnimator.start();
                Animation fadeOutAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.quick_fade_in);
                cardView.startAnimation(fadeOutAnimation);
                cardView.setVisibility(View.VISIBLE);
                childCardDown = true;
                break;

            case "SLIDE UP":
                cardAnimator.setInterpolator(new AnimationInterpolator());
                cardAnimator.setDuration(250);
                cardAnimator.start();
                Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.quick_fade_out);
                cardView.startAnimation(fadeInAnimation);
                removeChildFragment();
                childCardDown = false;
                break;
        }
    }

    public void slideCard(String SlideUpOrSlideDown) {
        View cardView = (View) getView().findViewById(R.id.bottom_row_fragment_container);
        ObjectAnimator cardAnimator = ObjectAnimator.ofFloat(cardView, "translationY", 30f, 270f);

        switch (SlideUpOrSlideDown) {
            case "SLIDE DOWN":
                cardAnimator.setDuration(250);
                cardAnimator.start();
                Animation fadeOutAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.quick_fade_in);
                cardView.startAnimation(fadeOutAnimation);
                cardView.setVisibility(View.VISIBLE);
                childCardDown = true;
                break;

            case "SLIDE UP":
                cardAnimator.setInterpolator(new AnimationInterpolator());
                cardAnimator.setDuration(250);
                cardAnimator.start();
                Animation fadeInAnimation = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.quick_fade_out);
                cardView.startAnimation(fadeInAnimation);
                removeChildFragment();
                childCardDown = false;
                break;
        }
    }

    public void removeChildFragment() {
        Fragment deleteFragment = getChildFragmentManager().findFragmentByTag(presentCard);
        getChildFragmentManager().beginTransaction().remove(deleteFragment).commit();
        if (deleteFragment != null) {
            getChildFragmentManager().beginTransaction().remove(deleteFragment).commit();
        }

    }

    public void removeFragment() {
        Fragment deleteFragment = getFragmentManager().findFragmentByTag(presentCard);
        getFragmentManager().beginTransaction().remove(deleteFragment).commit();
        if (deleteFragment != null) {
            getFragmentManager().beginTransaction().remove(deleteFragment).commit();
        }

    }

    public void replaceFragment(String newCard) {
        if (childCardDown) {
            switch(newCard) {
                case "addressFragment":
                    removeChildFragment();
                    createAddressCard();
                    break;

                case "XPFragment":
                    removeChildFragment();
                    createFBLoginCard();
                    break;

                case "photosFragment":
                    removeChildFragment();
                    createPhotosCard();
                    break;

                case "CONTINUE_REGISTERING":
                    removeFragment();
                    createContinueRegisteringCardVenue();
                    break;

                case "CONTINUE_REGISTERING_ATHOME":
                    removeChildFragment();
                    createContinueRegisteringCardAtHome();
                    break;

                case "BUSINESSCARD":
                    removeChildFragment();
                    createBusinessCard();
                    break;

                case "BUSINESSADDRESS":
                    removeChildFragment();
                    createBusinessAddressCard();
                    break;

                case "MENU":
                    removeChildFragment();
                    createMenuCard();
                    break;
            }
        }
    }

    public String findVenueTitle() {
        return venueTitle;
    }

    public String sendToBusiness_StepOne(String myString) {
        venueTitle = myString;
        registrationTitleAdded = true;
        return myString;
    }

    public String addToUserDataVenue(String userDataInput, String presentData) {
        venueUserDataSet.addData(userDataInput, presentData);
        venueUserDataSet.showData();
        return userDataInput;
    }

    public String getLatestDataVenue() {
        return venueUserDataSet.getLatestData();
    }

    public String getUserReviewVenue() {
        return venueUserDataSet.userReview();
    }

    public String addToUserDataAtHome(String userDataInput, String presentData) {
        atHomeUserDataSet.addData(userDataInput, presentData);
        atHomeUserDataSet.showData();
        return userDataInput;
    }

    public String getLatestDataAtHome() {
        return atHomeUserDataSet.getLatestData();
    }

    public String getUserReviewAtHome() {
        return atHomeUserDataSet.userReview();
    }

    public boolean atHomeRegistrationStatus() {
        return atHomeUserDataSet.atHomeRegistrationComplete();
    }

    public void registrationConfirmed() {
        changeTitlesAfterVenueRegistration();
        moveSecondRowAfterClick(1);
        slideCard("SLIDE UP");
        presentCard = "DEFAULT";
        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
        childCardDown = false;
    }

    public void completeAtHomeRegistration() {
        moveSecondRowAfterClick(1);
        slideCard("SLIDE UP");
        presentCard = "DEFAULT";
        NextAddressCardAnimation_For_Animator = "SLIDE DOWN";
        childCardDown = false;
        atHomeRegistrationCompleted = true;

    }

    public void fadeInAnimationOneView(View v) {
        ObjectAnimator fadeInAnimation = ObjectAnimator.ofFloat(v, "alpha", 0f, 1f);
        fadeInAnimation.setDuration(250);
        fadeInAnimation.start();
    }

    public void fadeAwayAnimationOneView(View v) {
        ObjectAnimator fadeAwayAnimation = ObjectAnimator.ofFloat(v, "alpha", 1f, 0f);
        fadeAwayAnimation.setDuration(250);
        fadeAwayAnimation.start();
    }

    public void changeTitlesAfterVenueRegistration() {
        venueRegistrationCompleted = true;
        fadeAwayAnimationOneView(creatingMerchantAccount);
        creatingMerchantAccount.setText(venueTitle);
        fadeInAnimationOneView(creatingMerchantAccount);
    }

    public boolean registrationStatus() {
        return venueRegistrationCompleted;
    }

    public String getSpecificUserData(int index) {
        return this.venueUserDataSet.getData(index);
    }

    public String checkUserData() {
        this.venueUserDataSet.showData();
        return venueTitle;
    }

    public boolean activateScrolling() {
        return this.venueUserDataSet.activateScrollView();
    }

    public boolean venueTitleAdded() {
        return registrationTitleAdded;
    }

    public void moveUpFirstRow(int slidingAnimationStatus) {
        //slideFirstRow(slidingAnimationStatus);
        Log.i("TAG", "connected moveUpFirstRow");
    }

    public void expandTopRow(String type) {
        ObjectAnimator expandTopRow = ObjectAnimator.ofFloat(cardTop, "scaleY", 1f, 1.1f);

        switch (type) {
            case "EXPAND":
                atHomeText.setVisibility(View.GONE);
                businessText.setVisibility(View.GONE);
                if (registrationTitleAdded) {
                    registrationTitle.setVisibility(View.GONE);
                }
                recyclerView.setVisibility(View.GONE);
                fadeAwayAnimationOneView(creatingMerchantAccount);
                //creatingMerchantAccount.setVisibility(View.INVISIBLE);
                expandTopRow.setDuration(150);
                expandTopRow.start();
                break;
            case "CONTRACT":
                atHomeText.setVisibility(View.VISIBLE);
                businessText.setVisibility(View.VISIBLE);
                if (registrationTitleAdded) {
                    registrationTitle.setVisibility(View.VISIBLE);
                }
                recyclerView.setVisibility(View.VISIBLE);
                fadeInAnimationOneView(creatingMerchantAccount);
                //creatingMerchantAccount.setVisibility(View.VISIBLE);
                expandTopRow.setInterpolator(new AnimationInterpolator());
                expandTopRow.setDuration(150);
                expandTopRow.start();
                break;
        }
        //expandTopRow.start();
    }

    public void updatePresentCard(String card) {
        presentCard = card;
    }

    public void openCardsForSecondRow(String type) {
        switch (type) {
            case "Venue":
                Log.i("TAG", "Venue");
                onVenueButtonClick(0);
                break;
            case "AtHome":
                Log.i("TAG", "AtHome");
                onClickButton(0);
                break;
        }
    }

    public void createTopSearchSuggestion() {

    }

    public void facebookLogin(){

    }

}
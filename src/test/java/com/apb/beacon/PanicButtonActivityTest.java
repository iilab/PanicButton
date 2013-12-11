package com.apb.beacon;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.robolectric.Robolectric.shadowOf;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;
import org.robolectric.shadows.ShadowViewGroup;

import android.view.ViewGroup;

import com.apb.beacon.alert.AlertStatus;
import com.apb.beacon.alert.PanicAlert;
import com.apb.beacon.test.support.PanicButtonRobolectricTestRunner;

@RunWith(PanicButtonRobolectricTestRunner.class)
public class PanicButtonActivityTest {
    @Mock
    private PanicAlert mockPanicAlert;
    private ShadowViewGroup alertStatusStrip;
    private CalculatorActivity activity;

    @Before
    public void setUp() {
        initMocks(this);
        activity = new CalculatorActivity() {
            @Override
            PanicAlert getPanicAlert() {
                return mockPanicAlert;
            }
        };
        activity.onCreate(null);
        activity.onPostCreate(null);
        ViewGroup alertStatusStripOrig = (ViewGroup) activity.findViewById(R.id.alert_status_strip);
        alertStatusStrip = shadowOf(alertStatusStripOrig);
    }


    @Test
    public void shouldShowRedAlertStripWhenAlertStatusIsActive(){
        when(mockPanicAlert.getAlertStatus()).thenReturn(AlertStatus.ACTIVE);
        activity.onResume();
        assertThat(alertStatusStrip.getBackgroundColor(), Is.is(Robolectric.application.getResources().getColor(R.color.active_color)));
    }

    @Test
    public void shouldShowAmberAlertStripWhenAlertStatusIsStandby(){
        when(mockPanicAlert.getAlertStatus()).thenReturn(AlertStatus.STANDBY);
        activity.onResume();
        assertThat(alertStatusStrip.getBackgroundColor(), Is.is(Robolectric.application.getResources().getColor(R.color.standby_color)));
    }
}
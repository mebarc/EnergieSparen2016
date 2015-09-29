package luh.energiesparen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;


public class RechtlichesActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView haftungsausschluss;
    private TextView haftungicons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechtliches);

        // Enable Toolbar and their Navigation Ability
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            if(getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        haftungsausschluss = (TextView) findViewById(R.id.textView_haftung01);
        haftungsausschluss.setText(Html.fromHtml("<h1>Haftungsausschluss (Disclaimer)</h1><p><strong>Haftung für Inhalte</strong></p>" +
                        " <p>Als Diensteanbieter sind wir gemäß § 7 Abs.1 TMG für eigene Inhalte" +
                        " auf diesen Seiten nach den allgemeinen Gesetzen verantwortlich. Nach §§ 8" +
                        " bis 10 TMG sind wir als Diensteanbieter jedoch nicht verpflichtet, übermittelte" +
                        " oder gespeicherte fremde Informationen zu überwachen oder nach Umständen zu forschen" +
                        ", die auf eine rechtswidrige Tätigkeit hinweisen. Verpflichtungen zur Entfernung oder" +
                        " Sperrung der Nutzung von Informationen nach den allgemeinen Gesetzen bleiben hiervon" +
                        " unberührt. Eine diesbezügliche Haftung ist jedoch erst ab dem Zeitpunkt der Kenntnis" +
                        " einer konkreten Rechtsverletzung möglich. Bei Bekanntwerden von entsprechenden Rechtsverletzungen" +
                        " werden wir diese Inhalte umgehend entfernen.</p> <p><strong>Haftung für Links" +
                        "</strong></p> <p>Unser Angebot enthält Links zu externen Webseiten Dritter, auf" +
                        " deren Inhalte wir keinen Einfluss haben. Deshalb können wir für diese fremden Inhalte" +
                        " auch keine Gewähr übernehmen. Für die Inhalte der verlinkten Seiten ist stets der jeweilige" +
                        " Anbieter oder Betreiber der Seiten verantwortlich. Die verlinkten Seiten wurden zum Zeitpunkt" +
                        " der Verlinkung auf mögliche Rechtsverstöße überprüft. Rechtswidrige Inhalte waren zum Zeitpunkt" +
                        " der Verlinkung nicht erkennbar. Eine permanente inhaltliche Kontrolle der verlinkten Seiten ist" +
                        " jedoch ohne konkrete Anhaltspunkte einer Rechtsverletzung nicht zumutbar. Bei Bekanntwerden von" +
                        " Rechtsverletzungen werden wir derartige Links umgehend entfernen.</p> <p><strong>Urheberrecht" +
                        "</strong></p> <p>Die durch die Seitenbetreiber erstellten Inhalte und Werke auf diesen Seiten " +
                        "unterliegen dem deutschen Urheberrecht. Die Vervielfältigung, Bearbeitung, Verbreitung und jede " +
                        "Art der Verwertung außerhalb der Grenzen des Urheberrechtes bedürfen der schriftlichen Zustimmung" +
                        " des jeweiligen Autors bzw. Erstellers. Downloads und Kopien dieser Seite sind nur für den privaten," +
                        " nicht kommerziellen Gebrauch gestattet. Soweit die Inhalte auf dieser Seite nicht vom Betreiber" +
                        " erstellt wurden, werden die Urheberrechte Dritter beachtet. Insbesondere werden Inhalte Dritter " +
                        "als solche gekennzeichnet. Sollten Sie trotzdem auf eine Urheberrechtsverletzung aufmerksam werden" +
                        ", bitten wir um einen entsprechenden Hinweis. Bei Bekanntwerden von Rechtsverletzungen werden wir " +
                        "derartige Inhalte umgehend entfernen.</p><p>" +
                        "<strong> Ende Haftungsausschluss </strong> </p>"));

        haftungicons = (TextView) findViewById(R.id.textView_haftung02);
        haftungicons.setText(Html.fromHtml("<div>Icons made by <a href=\"http://www.freepik.com\" title=\"Freepik\">Freepik</a> from <a href=\"http://www.flaticon.com\" title=\"Flaticon\">www.flaticon.com</a> is licensed under <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\">CC BY 3.0</a></div>"));

    }
}

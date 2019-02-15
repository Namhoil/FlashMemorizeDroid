package me.timgu.flashmemorize;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class flashcard extends AppCompatActivity {
    public List<Card> cards;
    public Deck dk;
    private TextView canvas;
    private int current_card = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcard);

        //assigning view element id
        canvas = findViewById(R.id.text_canvas);
        Toolbar toolbar = findViewById(R.id.toolbar_flashcard);
        setSupportActionBar(toolbar);

        //Receiving intent from main
        Intent intent = getIntent();
        String txtDeck = intent.getStringExtra(MainActivity.EXTRA_TXTDECK);
        dk = readTxtDeck(txtDeck);
        cards = dk.getDeck();
        showCard();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_flashcard,menu);
        return true;
    }

    public Deck readTxtDeck(String deck){
        Scanner scanner = new Scanner(deck);
        String line;
        boolean b;
        int indx;
        String front;
        String back;
        int ID = 0;
        List<Card> all_cards = new ArrayList<>();

        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            indx = line.indexOf((char) 9);
            if (indx >=0){
                front = line.substring(0,indx);
                back = line.substring(indx+1); //will this work?

                //front = front.replaceAll(getString(R.string.new_line_keyword), Character.toString((char) 10)); //will this work?
                //back = back.replaceAll(getString(R.string.new_line_keyword), Character.toString((char) 10)); //will this work?

                all_cards.add(new Card(front,back,ID));
                ID ++;
            }
        }
        Deck dk = new Deck("default_name",all_cards); //needs change name
        return dk;
    }

    public void showCard(){
        String text = cards.get(current_card).show();
        // somehow " -" 's space gets deleted in XML, have to hardcode it in here
        text = text.replaceAll(" -", Character.toString((char) 10) + (char)10);
        //needs more work;
        canvas.setText(text);
    }

    public void moveCurrentCard(int step){
        /*this is a mutator that changes the current_card variable;
        It is how we can navigate through the deck;
         */
        //needs more work;
        int nCards = dk.size;
        current_card = current_card + step;
        Toast.makeText(this, Integer.toString(current_card), Toast.LENGTH_SHORT).show();
        if (current_card == nCards){
            current_card = 0;
            Toast.makeText(this, "Reached End of Deck", Toast.LENGTH_SHORT).show();

        }else if (current_card < 0){
            current_card = dk.size - 1;
            Toast.makeText(this, "Reached Beginning of Deck", Toast.LENGTH_SHORT).show();

        }
    }

    public void prevCard(View view) {
        moveCurrentCard(-1);
        showCard();

    }

    public void nextCard(View view) {
        moveCurrentCard(1);
        showCard();
    }

    public void flipCard(View view) {
        cards.get(current_card).flip();
        showCard();
    }

    public void goodCard(View view) {
        moveCurrentCard(1);
        showCard();
    }

    public void badCard(View view) {
        moveCurrentCard(1);
        showCard();
    }

    public void shuffleCards(MenuItem item) {
        //needs more work
        dk.shuffle(1,1,0,0);
        cards = dk.getDeck();
        Toast.makeText(this,Integer.toString(cards.size()), Toast.LENGTH_SHORT).show();
        current_card = 0;
        showCard();
    }
}

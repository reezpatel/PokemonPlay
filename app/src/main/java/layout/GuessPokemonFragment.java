package layout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.reezx.android.pokeplay2.MainActivity;
import com.reezx.android.pokeplay2.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

import static com.reezx.android.pokeplay2.MainActivity.font_1;
import static com.reezx.android.pokeplay2.MainActivity.font_2;


public class GuessPokemonFragment extends Fragment {
    private OnGuessFragmentInteractionListener mListener;

    private Button btn1,btn2,btn3,btn4;
    private ImageView pokemonImage;
    private boolean inputAcceptingFlag;
    private TextView score;

    int life = 3;
    int correctAnsCount = 0;
    Drawable d1,d2,d3,d4;

    Bitmap pokemonCurrentImage;
    public GuessPokemonFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guess_pokemon, container, false);
        ImageView backButton = (ImageView) view.findViewById(R.id.imageViewGuessFramentBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        score = (TextView) view.findViewById(R.id.textViewGuessFragmentScore);

        TextView heading = (TextView) view.findViewById(R.id.textViewGuessFragmentHeading);
        heading.setTypeface(font_2);

        pokemonImage = (ImageView) view.findViewById(R.id.imageView);

        btn1 = (Button) view.findViewById(R.id.buttonGuessFragment1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonPressed(1,btn1);
            }
        });
        btn2 = (Button) view.findViewById(R.id.buttonGuessFragment2);
        btn3 = (Button) view.findViewById(R.id.buttonGuessFragment3);
        btn4 = (Button) view.findViewById(R.id.buttonGuessFragment4);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonPressed(2,btn2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonPressed(3,btn3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonPressed(4,btn4);
            }
        });

        btn1.setTypeface(font_1);
        btn2.setTypeface(font_1);
        btn3.setTypeface(font_1);
        btn4.setTypeface(font_1);

        d1 = btn1.getBackground();
        d2 = btn2.getBackground();
        d3 = btn3.getBackground();
        d4 = btn4.getBackground();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        runGame();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGuessFragmentInteractionListener) {
            mListener = (OnGuessFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

    }


    int currentPokemonId = 0;

    int makeScreen() {
        //Get 4 Pokemon
        int pokemonNumbers[] = getPokemonNames(1,649,4);

        //Get Correct Answer
        Random rand = new Random();
        int correctAnswer = rand.nextInt(4);

        currentPokemonId = pokemonNumbers[correctAnswer];

        //Set the Pokemon
        String fname = "artworks/" + pokemonNumbers[correctAnswer] + ".png";
        //Set Options
        btn1.setText(englishPokemon[pokemonNumbers[0]]);
        btn2.setText(englishPokemon[pokemonNumbers[1]]);
        btn3.setText(englishPokemon[pokemonNumbers[2]]);
        btn4.setText(englishPokemon[pokemonNumbers[3]]);

        InputStream is = null;
        try {
            is = MainActivity.assetManager.open(fname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pokemonCurrentImage = BitmapFactory.decodeStream(is);
        pokemonImage.setImageBitmap(getSilhouette());
        score.setText("0"+life);
        return correctAnswer;
    }


    int correctAns;

    void runGame() {
        resetButton();
        inputAcceptingFlag = false;
        correctAns = makeScreen();
        inputAcceptingFlag = true;

    }

    void displayCorrectAnswer() {
        Button btn = null;
        switch (correctAns+1) {
            case 1:
                btn = btn1;
                break;
            case 2:
                btn = btn2;
                break;
            case 3:
                btn = btn3;
                break;
            case 4:
                btn = btn3;
                break;
        }
        btn.setBackgroundResource(R.drawable.button_green_shape);
    }

    void resetButton() {
        if(life < 0) {
            mListener.onGuessFragmentInteraction(correctAnsCount);
        }
        btn1.setBackgroundResource(R.drawable.button_indigo_shape);
        btn2.setBackgroundResource(R.drawable.button_indigo_shape);
        btn3.setBackgroundResource(R.drawable.button_indigo_shape);
        btn4.setBackgroundResource(R.drawable.button_indigo_shape);
    }

    void ButtonPressed(int buttonID,Button btn) {
        if(inputAcceptingFlag) {
            inputAcceptingFlag = false;
            viewOrignalPokemon();
            if(correctAns == (buttonID-1)) {
                correctAnsCount++;
            }else {
                btn.setBackgroundResource(R.drawable.button_red_shape);
                life--;
            }
            displayCorrectAnswer();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    runGame();
                }
            },1000);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void viewOrignalPokemon() {
        String fname = "artworks/" + currentPokemonId + ".png";
        InputStream is = null;
        try {
            is = MainActivity.assetManager.open(fname);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pokemonImage.setImageDrawable(Drawable.createFromStream(is,"raw"));
    }

    public interface OnGuessFragmentInteractionListener {
        void onGuessFragmentInteraction(int score);
    }

    String englishPokemon[] = {"","bulbasaur", "ivysaur", "venusaur", "charmander", "charmeleon", "charizard", "squirtle", "wartortle", "blastoise", "caterpie", "metapod", "butterfree", "weedle", "kakuna", "beedrill", "pidgey", "pidgeotto", "pidgeot", "rattata", "raticate", "spearow", "fearow", "ekans", "arbok", "pikachu", "raichu", "sandshrew", "sandslash", "nidoran", "nidorina", "nidoqueen", "nidoran", "nidorino", "nidoking", "clefairy", "clefable", "vulpix", "ninetales", "jigglypuff", "wigglytuff", "zubat", "golbat", "oddish", "gloom", "vileplume", "paras", "parasect", "venonat", "venomoth", "diglett", "dugtrio", "meowth", "persian", "psyduck", "golduck", "mankey", "primeape", "growlithe", "arcanine", "poliwag", "poliwhirl", "poliwrath", "abra", "kadabra", "alakazam", "machop", "machoke", "machamp", "bellsprout", "weepinbell", "victreebel", "tentacool", "tentacruel", "geodude", "graveler", "golem", "ponyta", "rapidash", "slowpoke", "slowbro", "magnemite", "magneton", "farfetch'd", "doduo", "dodrio", "seel", "dewgong", "grimer", "muk", "shellder", "cloyster", "gastly", "haunter", "gengar", "onix", "drowzee", "hypno", "krabby", "kingler", "voltorb", "electrode", "exeggcute", "exeggutor", "cubone", "marowak", "hitmonlee", "hitmonchan", "lickitung", "koffing", "weezing", "rhyhorn", "rhydon", "chansey", "tangela", "kangaskhan", "horsea", "seadra", "goldeen", "seaking", "staryu", "starmie", "mr. mime", "scyther", "jynx", "electabuzz", "magmar", "pinsir", "tauros", "magikarp", "gyarados", "lapras", "ditto", "eevee", "vaporeon", "jolteon", "flareon", "porygon", "omanyte", "omastar", "kabuto", "kabutops", "aerodactyl", "snorlax", "articuno", "zapdos", "moltres", "dratini", "dragonair", "dragonite", "mewtwo", "mew", "chikorita", "bayleef", "meganium", "cyndaquil", "quilava", "typhlosion", "totodile", "croconaw", "feraligatr", "sentret", "furret", "hoothoot", "noctowl", "ledyba", "ledian", "spinarak", "ariados", "crobat", "chinchou", "lanturn", "pichu", "cleffa", "igglybuff", "togepi", "togetic", "natu", "xatu", "mareep", "flaaffy", "ampharos", "bellossom", "marill", "azumarill", "sudowoodo", "politoed", "hoppip", "skiploom", "jumpluff", "aipom", "sunkern", "sunflora", "yanma", "wooper", "quagsire", "espeon", "umbreon", "murkrow", "slowking", "misdreavus", "unown", "wobbuffet", "girafarig", "pineco", "forretress", "dunsparce", "gligar", "steelix", "snubbull", "granbull", "qwilfish", "scizor", "shuckle", "heracross", "sneasel", "teddiursa", "ursaring", "slugma", "magcargo", "swinub", "piloswine", "corsola", "remoraid", "octillery", "delibird", "mantine", "skarmory", "houndour", "houndoom", "kingdra", "phanpy", "donphan", "porygon2", "stantler", "smeargle", "tyrogue", "hitmontop", "smoochum", "elekid", "magby", "miltank", "blissey", "raikou", "entei", "suicune", "larvitar", "pupitar", "tyranitar", "lugia", "ho-oh", "celebi", "treecko", "grovyle", "sceptile", "torchic", "combusken", "blaziken", "mudkip", "marshtomp", "swampert", "poochyena", "mightyena", "zigzagoon", "linoone", "wurmple", "silcoon", "beautifly", "cascoon", "dustox", "lotad", "lombre", "ludicolo", "seedot", "nuzleaf", "shiftry", "taillow", "swellow", "wingull", "pelipper", "ralts", "kirlia", "gardevoir", "surskit", "masquerain", "shroomish", "breloom", "slakoth", "vigoroth", "slaking", "nincada", "ninjask", "shedinja", "whismur", "loudred", "exploud", "makuhita", "hariyama", "azurill", "nosepass", "skitty", "delcatty", "sableye", "mawile", "aron", "lairon", "aggron", "meditite", "medicham", "electrike", "manectric", "plusle", "minun", "volbeat", "illumise", "roselia", "gulpin", "swalot", "carvanha", "sharpedo", "wailmer", "wailord", "numel", "camerupt", "torkoal", "spoink", "grumpig", "spinda", "trapinch", "vibrava", "flygon", "cacnea", "cacturne", "swablu", "altaria", "zangoose", "seviper", "lunatone", "solrock", "barboach", "whiscash", "corphish", "crawdaunt", "baltoy", "claydol", "lileep", "cradily", "anorith", "armaldo", "feebas", "milotic", "castform", "kecleon", "shuppet", "banette", "duskull", "dusclops", "tropius", "chimecho", "absol", "wynaut", "snorunt", "glalie", "spheal", "sealeo", "walrein", "clamperl", "huntail", "gorebyss", "relicanth", "luvdisc", "bagon", "shelgon", "salamence", "beldum", "metang", "metagross", "regirock", "regice", "registeel", "latias", "latios", "kyogre", "groudon", "rayquaza", "jirachi", "deoxys", "turtwig", "grotle", "torterra", "chimchar", "monferno", "infernape", "piplup", "prinplup", "empoleon", "starly", "staravia", "staraptor", "bidoof", "bibarel", "kricketot", "kricketune", "shinx", "luxio", "luxray", "budew", "roserade", "cranidos", "rampardos", "shieldon", "bastiodon", "burmy", "wormadam", "mothim", "combee", "vespiquen", "pachirisu", "buizel", "floatzel", "cherubi", "cherrim", "shellos", "gastrodon", "ambipom", "drifloon", "drifblim", "buneary", "lopunny", "mismagius", "honchkrow", "glameow", "purugly", "chingling", "stunky", "skuntank", "bronzor", "bronzong", "bonsly", "mime jr.", "happiny", "chatot", "spiritomb", "gible", "gabite", "garchomp", "munchlax", "riolu", "lucario", "hippopotas", "hippowdon", "skorupi", "drapion", "croagunk", "toxicroak", "carnivine", "finneon", "lumineon", "mantyke", "snover", "abomasnow", "weavile", "magnezone", "lickilicky", "rhyperior", "tangrowth", "electivire", "magmortar", "togekiss", "yanmega", "leafeon", "glaceon", "gliscor", "mamoswine", "porygon-z", "gallade", "probopass", "dusknoir", "froslass", "rotom", "uxie", "mesprit", "azelf", "dialga", "palkia", "heatran", "regigigas", "giratina", "cresselia", "phione", "manaphy", "darkrai", "shaymin", "arceus", "victini", "snivy", "servine", "serperior", "tepig", "pignite", "emboar", "oshawott", "dewott", "samurott", "patrat", "watchog", "lillipup", "herdier", "stoutland", "purrloin", "liepard", "pansage", "simisage", "pansear", "simisear", "panpour", "simipour", "munna", "musharna", "pidove", "tranquill", "unfezant", "blitzle", "zebstrika", "roggenrola", "boldore", "gigalith", "woobat", "swoobat", "drilbur", "excadrill", "audino", "timburr", "gurdurr", "conkeldurr", "tympole", "palpitoad", "seismitoad", "throh", "sawk", "sewaddle", "swadloon", "leavanny", "venipede", "whirlipede", "scolipede", "cottonee", "whimsicott", "petilil", "lilligant", "basculin", "sandile", "krokorok", "krookodile", "darumaka", "darmanitan", "maractus", "dwebble", "crustle", "scraggy", "scrafty", "sigilyph", "yamask", "cofagrigus", "tirtouga", "carracosta", "archen", "archeops", "trubbish", "garbodor", "zorua", "zoroark", "minccino", "cinccino", "gothita", "gothorita", "gothitelle", "solosis", "duosion", "reuniclus", "ducklett", "swanna", "vanillite", "vanillish", "vanilluxe", "deerling", "sawsbuck", "emolga", "karrablast", "escavalier", "foongus", "amoonguss", "frillish", "jellicent", "alomomola", "joltik", "galvantula", "ferroseed", "ferrothorn", "klink", "klang", "klinklang", "tynamo", "eelektrik", "eelektross", "elgyem", "beheeyem", "litwick", "lampent", "chandelure", "axew", "fraxure", "haxorus", "cubchoo", "beartic", "cryogonal", "shelmet", "accelgor", "stunfisk", "mienfoo", "mienshao", "druddigon", "golett", "golurk", "pawniard", "bisharp", "bouffalant", "rufflet", "braviary", "vullaby", "mandibuzz", "heatmor", "durant", "deino", "zweilous", "hydreigon", "larvesta", "volcarona", "cobalion", "terrakion", "virizion", "tornadus", "thundurus", "reshiram", "zekrom", "landorus", "kyurem", "keldeo", "meloetta", "genesect"};


    public static int[] getPokemonNames(int start, int end, int count) {
        Random rng = new Random();

        int[] result = new int[count];
        int cur = 0;
        int remaining = end - start;
        for (int i = start; i < end && count > 0; i++) {
            double probability = rng.nextDouble();
            if (probability < ((double) count) / (double) remaining) {
                count--;
                result[cur++] = i;
            }
            remaining--;
        }
        return result;
    }

    Bitmap getSilhouette() {
        Bitmap bitmap = pokemonCurrentImage.copy(pokemonCurrentImage.getConfig(),true);

        for(int y=0;y<pokemonCurrentImage.getHeight();y++) {
            for(int x=0;x<pokemonCurrentImage.getWidth();x++) {
                if(bitmap.getPixel(x,y) != 0) {
                    bitmap.setPixel(x, y, Color.parseColor("#000000"));
                }
            }
        }
        return bitmap;
    }
}

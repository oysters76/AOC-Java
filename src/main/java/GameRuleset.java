import java.util.HashMap;

public class GameRuleset {

    private final String [] PLAYER_SYMBOLS = new String[]{"X", "Y", "Z"};
    private final String P_ROCK = PLAYER_SYMBOLS[0];
    private final String P_PAPER = PLAYER_SYMBOLS[1];
    private final String P_SCISSOR = PLAYER_SYMBOLS[2];

    private final String O_ROCK = "A";
    private final String O_PAPER = "B";
    private final String O_SCISSOR = "C";

    private final String ACT_LOSS = "X";
    private final String ACT_DRAW = "Y";
    private final String ACT_WIN  = "Z";

    private final int [] LOOKUP_VALUES = new int[]{6,0,3,1,2,3};

    private final int I_WIN = 0;
    private final int I_LOSS = 1;
    private final int I_DRAW = 2;
    private final int I_ROCK = 3;
    private final int I_PAPER = 4;
    private final int I_SCISSOR = 5;

    private final int STATE_WIN = 0;
    private final int STATE_LOSS = 1;
    private final int STATE_DRAW = 2;


    private HashMap<String, String> gameRuleWin;
    private HashMap<String, String> gameruleDraw;
    private HashMap<String, Integer> symbolMap;

    public GameRuleset(){
        initGameRuleWin();
        initGameRulesDraw();
        initSymbolMap();
    }


    public void initGameRuleWin(){
        this.gameRuleWin = new HashMap<>();
        this.gameRuleWin.put(O_ROCK, P_PAPER);
        this.gameRuleWin.put(O_PAPER, P_SCISSOR);
        this.gameRuleWin.put(O_SCISSOR, P_ROCK);
    }

    public void initSymbolMap(){
        symbolMap = new HashMap<>();
        symbolMap.put(P_ROCK, LOOKUP_VALUES[I_ROCK]);
        symbolMap.put(P_PAPER, LOOKUP_VALUES[I_PAPER]);
        symbolMap.put(P_SCISSOR, LOOKUP_VALUES[I_SCISSOR]);
    }

    public void initGameRulesDraw(){
        this.gameruleDraw = new HashMap<>();
        this.gameruleDraw.put(O_ROCK, P_ROCK);
        this.gameruleDraw.put(O_SCISSOR, P_SCISSOR);
        this.gameruleDraw.put(O_PAPER, P_PAPER);
    }

    public int getSymbolValue(String player){
        return symbolMap.get(player);
    }

    public int evaluateGame(String opponent, String player){
        boolean doesPlayerWin = gameRuleWin.get(opponent).equals(player);
        boolean doesGameDraw = gameruleDraw.get(opponent).equals(player);
        if (doesPlayerWin){
            return STATE_WIN;
        }else if (doesGameDraw){
            return STATE_DRAW;
        }
        return STATE_LOSS;
    }

    public String getAction(String opponent, String actionType){
        switch (actionType){
            case ACT_WIN:{
                return gameRuleWin.get(opponent);
            }
            case ACT_DRAW:{
                return gameruleDraw.get(opponent);
            }
            case ACT_LOSS:{
                String winSymbol = gameRuleWin.get(opponent);
                String drawSymbol = gameruleDraw.get(opponent);
                for (String symbol : PLAYER_SYMBOLS){
                    if (symbol.equals(winSymbol) || symbol.equals(drawSymbol))
                        continue;
                    return symbol;
                }
                return null;
            }
        }
        return null;
    }

    public int getScoreForGame(int state){
        return switch (state) {
            case STATE_WIN -> LOOKUP_VALUES[I_WIN];
            case STATE_LOSS -> LOOKUP_VALUES[I_LOSS];
            case STATE_DRAW -> LOOKUP_VALUES[I_DRAW];
            default -> 0;
        };
    }


}

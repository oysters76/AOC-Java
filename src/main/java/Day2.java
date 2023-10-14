import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Day2 {
    GameRuleset game;

    public interface StrategyEval{
        int evaluate(String strategy);
    }

    public Day2(GameRuleset game){
        this.game = game;
    }
    public int evaluateStrategy(String strategy){
        String [] symbols = strategy.split(" ");
        return game.getScoreForGame(game.evaluateGame(symbols[0], symbols[1])) + game.getSymbolValue(symbols[1]);
    }

    public String getPlayerAction(String opponent, String actionType){
        return game.getAction(opponent, actionType);
    }

    public int evaluateAction(String strategy){
        String [] symbols = strategy.split(" ");
        String opponentSymbol = symbols[0];
        String actionType = symbols[1];
        String playerSymbol = getPlayerAction(opponentSymbol, actionType);
        return game.getScoreForGame(game.evaluateGame(opponentSymbol, playerSymbol)) + game.getSymbolValue(playerSymbol);
    }

    public int getTotalScore(ArrayList<String> strategyList, StrategyEval evalFunction){ // <- functional interface
        int totalScore = 0;
        for (String strategy : strategyList)
            totalScore += evalFunction.evaluate(strategy); // < functional interface
        return totalScore;
    }

    public ArrayList<String> readPuzzleFile(String fileName){
        InputStream stream = Day2.class.getResourceAsStream(fileName);
        ArrayList<String> strategyList = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(stream))){
            String line;
            while ((line= br.readLine())!=null){
                strategyList.add(line);
            }
            return strategyList;
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public int pipelineForPart1(String filename){
        return getTotalScore(
                readPuzzleFile(filename),
                this::evaluateStrategy
        );
    }

    public int pipelineForPart2(String filename){
        return getTotalScore(
                readPuzzleFile(filename),
                this::evaluateAction
        );
    }

    public static void main(String[] args) {
        GameRuleset ruleset = new RockPaperScissorV2();
        Day2 day2 = new Day2(ruleset);
        System.out.println("answer 1: " + day2.pipelineForPart1("day2.txt"));
        System.out.println("answer 2: " + day2.pipelineForPart2("day2.txt"));
    }


}

// J-Crypt by JayMar921
public class MainClass {
    public static void main(String... args){
        if(args.length!=0){
            if (args[0].equalsIgnoreCase("-cli")){
                if(args.length>1){
                    if(args[1].equals("-ansi"))
                        new ExecutorANSI().excecuteCLI();
                }else
                    new Executor().excecuteCLI();
            }

        }
  }
}

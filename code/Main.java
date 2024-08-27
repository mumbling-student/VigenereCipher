public class Main {
    public static void main(String[] args) {
        if (args.length == 3) {
            if (args[0].equals("encode")) {
                VigenereCipherService.encode(args[1], args[2]);
            } else if (args[0].equals("decode")) {
                VigenereCipherService.decode(args[1], args[2]);
            }
        } else {
            System.out.println("Wrong amount of arguments");
        }
    }
}

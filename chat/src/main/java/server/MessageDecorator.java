package server;

/**
 * Created by Trofim Moshik on 15.03.2018.
 */
class MessageDecorator {
    private String income;

    MessageDecorator(String income) {
        this.income = income;
    }

    String decorate() {
        return income + " DATE";
    }
}

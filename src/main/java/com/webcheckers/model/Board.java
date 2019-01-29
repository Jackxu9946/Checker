package com.webcheckers.model;



import java.util.ArrayList;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private int gameNumber;
    private BoardView  BV;
    private Player     redPlayer;
    private Player     whitePLayer;
    private ArrayList<Piece> AllPieces = new ArrayList<>();
    private ArrayList<Piece>  redPlayerPiece;
    private ArrayList<Piece>  whitePlayerPiece;
    private ArrayList<Piece>  HighlightPiece = new ArrayList<>();
    private boolean RedCheat = false;
    private boolean WhiteCheat = false;
    public Board(Player redPlayer,Player whitePlayer, BoardView bv, int gameNumber){

        //System.out.println("asdada");
        this.gameNumber = gameNumber;
        this.redPlayer = redPlayer;
        this.whitePLayer = whitePlayer;
        this.BV = bv;
        ArrayList<Piece> redPlayerPiece = this.makeRedPlayerPiece();
        ArrayList<Piece> whitePlayerPiece = this.makeWhitePlayerPiece();
        this.redPlayerPiece = redPlayerPiece;
        this.whitePlayerPiece = whitePlayerPiece;
        ArrayList<Piece> EmptyList = new ArrayList<>();
        EmptyList.addAll(redPlayerPiece);
        EmptyList.addAll(whitePlayerPiece);
        this.AllPieces = EmptyList;
        //Piece  TestP = new Piece(3,4,false,whitePlayer);
        //bv.iterator().get(3).iterator().get(4).addPiece(TestP);
        this.ReAll();
        /**
        Piece  TestP2 = new Piece(3,6,false,whitePlayer);
        Piece TestP3 = new Piece (4,5,false,whitePlayer);
        Piece TestP4 = new Piece(4,7,false,whitePlayer);
        Piece TestP5 = new Piece(4,1,false,whitePlayer);
        Piece TestP6 = new Piece(4,3,false,whitePlayer);
         */
        /**
        bv.iterator().get(3).iterator().get(6).addPiece(TestP2);
        bv.iterator().get(4).iterator().get(1).addPiece(TestP2);
        bv.iterator().get(4).iterator().get(3).addPiece(TestP2);
        bv.iterator().get(4).iterator().get(5).addPiece(TestP2);
        bv.iterator().get(4).iterator().get(7).addPiece(TestP2);
        this.TestingSingle();
         **/
    }

    /**
     * work
     */

    /**
     * Initialize all the pieces for redPlayer
     * The boardview is initialize from down to top which means index 0 is the bottom row
     */
    public ArrayList<Piece> makeRedPlayerPiece(){
        ArrayList<Piece> redPlayerPiece  = new ArrayList<>();
        ArrayList<Row> RowIterator = BV.iterator();
        for(int row = 0; row < 3; row++){
            List<Space> AllSpace = RowIterator.get(row).iterator();
            for(int col =0; col < 8; col ++){
                if(AllSpace.get(col).isValid()){
                    //true is red false is white
                    //
                    Piece newPiece = new Piece(row,col,true, redPlayer);
                    BV.iterator().get(row).iterator().get(col).addPiece(newPiece);
                    redPlayerPiece.add(newPiece);
                }
            }
        }
        redPlayer.setPiece(redPlayerPiece);
        return redPlayerPiece;
    }

    /**
     * Initialize all the pieces for whitePlayer
     */
    public ArrayList<Piece> makeWhitePlayerPiece(){
        ArrayList<Piece> whitePlayerPiece = new ArrayList<>();
        ArrayList<Row> RowIterator = BV.iterator();
        for(int row=5; row < 8; row++){
            List<Space> AllSpace = RowIterator.get(row).iterator();
            for(int col = 0; col<8;col++){
                if(AllSpace.get(col).isValid()){
                    Piece newPiece = new Piece(row,col,false, whitePLayer);
                    BV.iterator().get(row).iterator().get(col).addPiece(newPiece);
                    whitePlayerPiece.add(newPiece);
                }
            }

        }
        whitePLayer.setPiece(whitePlayerPiece);
        return whitePlayerPiece;
    }


    public ArrayList<Row> iterator(){
        return BV.iterator();
    }

    public BoardView getBV() {
        return BV;
    }

    /**
     * This function will find all the possible back jump move of that piece
     * Will return an arraylist<Piece> containing all the possible jump moves
     */
    public ArrayList<Piece> FindBackJump(Piece CurrentPiece, BoardView BV, Player P1){
        int Row = CurrentPiece.getRow();
        int Column = CurrentPiece.getColumn();
        ArrayList<Row> IteRow = BV.iterator();
        ArrayList<Piece> BkJump = new ArrayList<>();
        for(int n = 0; n <2; n++){
            int RowTemp = CurrentPiece.getRow();
            int ColumnTemp = CurrentPiece.getColumn();
            /**
             * Check lower right
              */
            if(n==0){
                RowTemp --;
                ColumnTemp --;
                /**
                 * Checking if it is within bounds
                 */
                if (RowTemp < 7 && RowTemp > 0 && ColumnTemp < 7 && ColumnTemp > 0) {
                    /**
                     * Checking if there is a piece
                     */
                    if (!(IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece() == null) && (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getColor() != Piece.color.GREEN
                            && (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getColor() != Piece.color.YELLOW)) ) {
                        /**
                         * Checking if the piece is owned by an opponent
                         */
                        if (!IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getPlayer().getName().equals(P1.getName())) {
                            RowTemp--;
                            ColumnTemp--;
                            /**
                             * Checking to see if the space two down and two to the right is empty
                             */
                            if (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece() == null) {
                                Piece TempPiece = new Piece(RowTemp, ColumnTemp);
                                Position tempPos = new Position(RowTemp, ColumnTemp);
                                if(!CurrentPiece.getPastLocation().contains(tempPos))
                                    BkJump.add(TempPiece);
                                }
                            }
                        }
                    }
                }


            /**
             * Check lower left
             */
            else{
                RowTemp --;
                ColumnTemp ++;
                /**
                 * Checking if it is within bounds
                 */
                if(RowTemp < 7 && RowTemp >0 && ColumnTemp < 7 && ColumnTemp >0) {
                    /**
                     * Checking if there is a piece
                     */
                    if (!(IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece() == null) && (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getColor() != Piece.color.GREEN
                            && (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getColor() != Piece.color.YELLOW)) ){
                        //System.out.println(IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece());

                        /**
                         * Checking to see the piece is owned by opponent
                         */
                        if (!IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getPlayer().getName().equals(P1.getName())) {
                            RowTemp--;
                            ColumnTemp++;
                            /**
                             * Checking to see if the space 2 units down and 2 units to the left is empty
                             */
                            if (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece() == null) {
                                Piece TempPiece = new Piece(RowTemp, ColumnTemp);
                                Position tempPos = new Position(RowTemp, ColumnTemp);
                                if(!CurrentPiece.getPastLocation().contains(tempPos))
                                    BkJump.add(TempPiece);

                            }
                        }
                    }
                }
            }

        }
        return BkJump;
    }

    /**
     * Finds all the possible single forward jump moves and then return an arraylist that contain all the single jumps
     * @param CurrentPiece Piece
     * @param BV Boardview
     * @param P1 Player
     * @return Arraylist<Piece>
     */
    public ArrayList<Piece> FindForwardJump(Piece CurrentPiece, BoardView BV, Player P1){
        ArrayList<Row> IteRow = BV.iterator();
        ArrayList<Piece> FdJump = new ArrayList<>();
        for (int n =0; n < 2; n++){
            int RowTemp = CurrentPiece.getRow();
            int ColumnTemp = CurrentPiece.getColumn();
            if (n ==0 ) {
                /**
                 * Checking upper right
                 */
                RowTemp++;
                ColumnTemp--;
                /**
                 * Making sure it is within range
                 */
                if(RowTemp < 7 && RowTemp > 0 && ColumnTemp < 7 && ColumnTemp > 0) {
                    //System.out.println("Row " + RowTemp);
                    //System.out.println("Col " + ColumnTemp);
                    /**
                     * Checking if there is a piece on the upper right
                     */
                    if(!(IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece() == null) && (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getColor() != Piece.color.GREEN
                            && (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getColor() != Piece.color.YELLOW)) ) {
                        //System.out.println("Row " + RowTemp);
                        //System.out.println("Col " + ColumnTemp);
                        //System.out.println(IteRow.get(RowTemp).iterator().get(ColumnTemp));
                        /**
                         * Checking if the upper right piece is owned by the opponent
                         */
                        if (!IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getPlayer().getName().equals(P1.getName())) {

                            RowTemp++;
                            ColumnTemp--;
                            /**
                             * Checking if the space 2 units up and to the right is empty aka can be put
                             */
                            if (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece() == null) {
                                Piece TempPiece = new Piece(RowTemp, ColumnTemp);
                                Position tempPos = new Position(RowTemp, ColumnTemp);
                                if(!CurrentPiece.getPastLocation().contains(tempPos))
                                    FdJump.add(TempPiece);
                            }

                        }
                    }
                }
            }
            if (n ==1 ) {
                /**
                 * Checking upper left
                 */
                RowTemp++;
                ColumnTemp++;
                /**
                 * Making sure upper left is within bounds
                 */
                if (RowTemp < 7 && RowTemp > 0 && ColumnTemp < 7 && ColumnTemp > 0) {

                    /**
                     * Checking if there is a piece on the upper left
                     */
                    if (!(IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece() == null) && (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getColor() != Piece.color.GREEN
                    && (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getColor() != Piece.color.YELLOW)) ) {
                        /**
                         * Checking if a piece is owned by the opponent
                         */
                        if (!IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece().getPlayer().getName().equals(P1.getName())) {
                            RowTemp++;
                            ColumnTemp++;
                            /**
                             * Checking if the piece 2 units up and 2 units left is empty
                             */
                            if (IteRow.get(RowTemp).iterator().get(ColumnTemp).getPiece() == null) {
                                Piece TempPiece = new Piece(RowTemp, ColumnTemp);
                                //System.out.println(TempPiece);
                                Position tempPos = new Position(RowTemp, ColumnTemp);
                                if(!CurrentPiece.getPastLocation().contains(tempPos))
                                    FdJump.add(TempPiece);
                            }

                        }
                    }
                }
            }
        }
        return FdJump;
    }


    /**
     * Check if the piece is a king and then update the JumpList accordingly.
     * @param pl
     */
    public void SetJumpMoves(Piece pl){
        /**If the piece is a king
         *
         */
        if (pl.getType().equals(Piece.type.KING)){
           ArrayList<Piece> ForwardJP = FindForwardJump(pl,this.BV,pl.getPlayer());
           ArrayList<Piece> BackJP  = FindBackJump(pl,this.BV,pl.getPlayer());
           ForwardJP.addAll(BackJP);
           pl.setJumpMove(ForwardJP);
        }

        /**
         * if players is red**/
        else if(pl.getColor().equals(Piece.color.RED)){
            //System.out.println("Red");
            ArrayList<Piece> ForwardJP = FindForwardJump(pl,this.BV,pl.getPlayer());
            pl.setJumpMove(ForwardJP);
        }

        /**
         * Last case if player is white
         */
        else {
            ArrayList<Piece> BackJP = FindBackJump(pl,this.BV,pl.getPlayer());
            for(Piece K: BackJP){

            }
            pl.setJumpMove(BackJP);
        }
    }

    /**
     * This should be called everytime a move has been made
     * It will find the possible jump move for all the pieces on the board
     * and reset them
     */
    public void ReAllSingleJump(){
        this.DeleteHighLight();
        for (Piece P: redPlayerPiece){
            //System.out.println(P);
            this.SetJumpMoves(P);
        }
        for (Piece  P2:whitePlayerPiece){
            //System.out.println(P2);
            this.SetJumpMoves(P2);
        }
        this.setHighlightPiece();
    }

    /**
     * Validatemove takes in two positions, start,end
     * returns a String "T_Message" or "F_Message"
     */


    public String validateMoves(Position Start,Position End,Player P1){
        int startrow = Start.row;
        int startcol = Start.cell;
        int endrow = End.row;
        int endcol = End.cell;
        Piece StartP = new Piece(startrow,startcol);
        Piece EndP  = new Piece(endrow,endcol);
        //this.ReAllSingleJump();
        //this.ReSimpleMove();
        this.ReAll();
        System.out.println(redPlayer.getMyTurn() + " red player ");
        System.out.println(whitePLayer.getMyTurn() + " white player ");
        if(P1.getColor() == true){
            ArrayList<Piece> redPlayerList = this.redPlayerPiece;
            //Check if there is a possible simple move
            if(P1.getCurrentPiece() != null)
            {
                if(P1.getCurrentPiece().getMadeAMove() == true)
                {
                    return "F_Already made a move";
                }
            }
            if(this.isSingleJumpPossible(redPlayerList)){
                //Checks if this Start Piece even have a single jump
                if(!(this.isThisJumpValid(redPlayerList,StartP))){
                    return "F_Have to make a jump move";
                }
                //This piece have a jump move possible
                else{
                    //The jump move is valid
                    if((CheckJumpMove(StartP,EndP,redPlayerList))){
                       return "T_ Valid jump";
                    }
                    else{
                        //the jump move is invalid
                        return "F_Invalid Jump";
                    }

                }

            }
            //Single jump is not possible, simple move is possible
            else{
                //Check if the simple move is valid
                //System.out.println(redPlayer.getMyTurn());
                if(CheckSimpleMove(StartP,EndP,redPlayerList)){
                    if(P1.getCurrentPiece() != null)
                    {
                        if(P1.getCurrentPiece().getMadeAJump() == true || P1.getCurrentPiece().getMadeAMove() == true)
                        {
                            return "F_Already made a move";
                        }
                    }
                    return "T_Valid simple move";
                }
                else{
                    return "F_Invalid simple move";
                }


            }
        }
        else{
            ArrayList<Piece> whitePlayerPiece = this.whitePlayerPiece;
            //Check if there is a possible simple move
            if(P1.getCurrentPiece() != null)
            {
                if(P1.getCurrentPiece().getMadeAMove() == true)
                {
                    return "F_Already made a move";
                }
            }
            if(this.isSingleJumpPossible(whitePlayerPiece)){
                //Checks if this Start Piece even have a single jump
                if(!(this.isThisJumpValid(whitePlayerPiece,StartP))){
                    return "F_Have to make a jump move";
                }
                //This piece have a jump move possible
                else{
                    //When the jump move is valid
                    if((CheckJumpMove(StartP,EndP,whitePlayerPiece))){
                        return "T_ Valid move";
                    }
                    //When the jump move is invalid
                    else{
                        return "F_InvalidJump";
                    }

                }

            }
            //Single jump is not possible, simple move is possible
            else{
                //System.out.println("WhitePlayer " + whitePLayer.getMyTurn());
                //Check if the simple move is valid
                if(CheckSimpleMove(StartP,EndP,whitePlayerPiece)){
                    if(P1.getCurrentPiece() != null)
                    {
                        if(P1.getCurrentPiece().getMadeAJump() == true || P1.getCurrentPiece().getMadeAMove() == true)
                        {
                            return "F_Already made a move";
                        }
                    }
                    return "T_Valid simple move";
                }
                else{
                    return "F_Invalid simple move";
                }


            }





        }
    }

    /**
     * Checks if the simple move is valid
     * @param Start Piece
     * @param End Piece
     * @param AllPossible ArrayList<Piece></Piece>
     * @return boolean
     */
    public boolean CheckSimpleMove(Piece Start,Piece End,ArrayList<Piece> AllPossible){
        for(Piece K:AllPossible){
            if(Start.isEquals(K)){
                //System.out.println(K.getSimpleMovePossible());
                for(Piece SimpleMovePiece: K.getSimpleMovePossible()){
                    if(End.isEquals(SimpleMovePiece)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if the jump move is valid
     * @param Start Piece
     * @param End Piece
     * @param AllPossible ArrayList<Piece></Piece>
     * @return boolean
     */
    public boolean CheckJumpMove(Piece Start,Piece End,ArrayList<Piece> AllPossible){
        for(Piece K: AllPossible){
            if(Start.isEquals(K)){
                for(Piece JumpPiece: K.getSingleJumpPossibles()){
                    if(JumpPiece.isEquals(End)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if there is a valid jump move for this piece
     * @param JP ArrayList<Piece></Piece>
     * @param P Piece
     * @return boolean
     */
    public boolean isThisJumpValid(ArrayList<Piece> JP,Piece P){
        for(Piece K: JP){
            //Check if they are on the same space
            if(K.isEquals(P)){
                if (K.getSingleJumpPossibles().size()>0){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check if there is a valid jump on the entire board for this player
     * @param JP ArrayList<Piece></Piece>
     * @return boolean
     */
    public boolean isSingleJumpPossible(ArrayList<Piece> JP){
        for(Piece J: JP){
            if(J.getSingleJumpPossibles().size() > 0){
                return true;
            }
        }
        return false;
    }


    public void ReSimpleMove(){
        for(Piece P: redPlayerPiece){
            P.simpleMove(this.BV);
        }
        for(Piece P2: whitePlayerPiece){
            P2.simpleMove(this.BV);

        }
    }


    public void ReAll(){
        //System.out.println("Before deleting");
        this.DeleteHighLight();
        //System.out.println("After deleting");
        this.ReAllSingleJump();
        this.ReSimpleMove();
    }


    public Piece JumpTempMoves(Position Pos, Piece Pl){
        //this.DeleteHighLight();
       Pl.setRow(Pos.row);
       Pl.setColumn(Pos.cell);
       this.SetJumpMoves(Pl);
       Pl.simpleMove(this.BV);
       return Pl;
    }



    public void BoardUpdate(ArrayList<Position> Po,Player PL){
        this.getBV().iterator().get(Po.get(Po.size()-1).row).iterator().get(Po.get(Po.size()-1).cell).addPiece(PL.getCurrentPiece());
        if(Po.size()>2){

            UpdateJumpMove(Po,PL);
        }
        else
        {
            DelPiece(Po.get(0));
        }

    }

    /**
     * This will delete a piece at that position from the board itself
     * @param pos Position
     */
    public void DelPiece(Position pos){
        //Replace the piece at the position with a null which is effectively deleting a piece
        this.getBV().iterator().get(pos.row).iterator().get(pos.cell).addPiece(null);
    }

    /**
     * This is how the board will update when jump moves are finalized
     * @param Pos Position
     * @param P1 Player
     */
    public void UpdateJumpMove(ArrayList<Position> Pos,Player P1){
        int lastindex = Pos.size()-1;

        for(int n =0; n<lastindex; n++){
            Position temp = Pos.get(n);
            Piece newtempP = new Piece(temp.row,temp.cell);
            DelPiece(temp);
            DelPieceFromPlayer(newtempP);
        }
    }

    /**
     * Delete the piece from the player
     * @param P Piece
     */
    public void DelPieceFromPlayer(Piece P){
        int WheretoDel = -1;
        int Whoisit = 0;
        for(Piece K:this.redPlayerPiece){
            if(K.isEquals(P)){
                WheretoDel = redPlayerPiece.indexOf(K);
                Whoisit = 1;
            }
        }
        for(Piece K2:this.whitePlayerPiece){
            if(K2.isEquals(P)){
                WheretoDel = whitePlayerPiece.indexOf(K2);
                Whoisit = 2;
            }
        }
        if(WheretoDel != -1){
            if(Whoisit == 1){
                redPlayerPiece.remove(WheretoDel);
            }
            else{
                whitePlayerPiece.remove(WheretoDel);
            }
        }
    }

    public void setHighlightPiece(){

        ArrayList<Piece> PL = new ArrayList<>();
        if(whitePLayer.getMyTurn() && whitePLayer.getCurrentPiece() != null ){
            System.out.println("white player cheat no move made");

            /**When the current player already made a move**/
            if(whitePLayer.getCurrentPiece().getMadeAJump()){
                boolean  CanJump = whitePLayer.getCurrentPiece().getSingleJumpPossibles().size()>0;
                whitePLayer.getCurrentPiece().SetAllPossible(CanJump);
                if(CanJump) {
                    for (Piece JK : whitePLayer.getCurrentPiece().GetAllPossible()) {
                        if (!(PL.contains(JK))) {
                            if (this.getBV().iterator().get(JK.getRow()).iterator().get(JK.getColumn()).getPiece() == null) {

                                JK.setPieceType(whitePLayer.getCurrentPiece().getType());
                                JK.setColor(Piece.color.GREEN);
                                JK.setPlayer(new Player("dsahjkncakjn cmkndjkanfkajnfjak"));
                                PL.add(JK);
                            }

                        }
                    }
                }

        }
        }
        if(redPlayer.getMyTurn() && redPlayer.getCurrentPiece() != null){
            System.out.println("Red player cheat  move made");
            System.out.println(redPlayer.getCurrentPiece().getMadeAMove());
            if(redPlayer.getCurrentPiece().getMadeAJump()){
                boolean  CanJump = redPlayer.getCurrentPiece().getSingleJumpPossibles().size()>0;
                redPlayer.getCurrentPiece().SetAllPossible(CanJump);
                if(CanJump) {
                    for (Piece JK : redPlayer.getCurrentPiece().GetAllPossible()) {
                        if (!(PL.contains(JK))) {
                            if (this.getBV().iterator().get(JK.getRow()).iterator().get(JK.getColumn()).getPiece() == null) {

                                JK.setPieceType(redPlayer.getCurrentPiece().getType());
                                JK.setColor(Piece.color.GREEN);
                                JK.setPlayer(new Player("dsahjkncakjn cmkndjkanfkajnfjak"));
                                PL.add(JK);
                            }

                        }
                    }
                }
            }


        }
        if(WhiteCheat == true && whitePLayer.getMyTurn() && whitePLayer.getCurrentPiece()==null) {
            System.out.println("white player cheat no move made");

            boolean YesJump  = this.CanThisPlayerJump(whitePLayer);
            for (Piece P:whitePlayerPiece){
                     P.SetAllPossible(YesJump);
                    }
            for (Piece K : whitePlayerPiece) {
                for (Piece JK : K.GetAllPossible()) {

                    if (!(PL.contains(JK))) {
                        if(this.getBV().iterator().get(JK.getRow()).iterator().get(JK.getColumn()).getPiece() == null) {

                            JK.setPieceType(K.getType());
                            JK.setColor(Piece.color.GREEN);
                            JK.setPlayer(new Player("dsahjkncakjn cmkndjkanfkajnfjak"));
                            PL.add(JK);
                        }
                    }
                }
                //WhitePlayer piece is reprensented by Green
            }
        }
        //System.out.println(redPlayer.getMyTurn());
        if(RedCheat == true && redPlayer.getMyTurn() && redPlayer.getCurrentPiece() == null) {
            System.out.println("Red player cheat no move made");
            boolean YesJump  = this.CanThisPlayerJump(redPlayer);
            //System.out.println(YesJump);
            for (Piece P:redPlayerPiece){
                 P.SetAllPossible(YesJump);
            }
            for (Piece J : redPlayerPiece) {
                //Yellow represents redplayer pieces
                for (Piece JK : J.GetAllPossible()) {
                    //System.out.println(J.GetAllPossible().size());
                    if (!(PL.contains(JK))) {
                        if(this.getBV().iterator().get(JK.getRow()).iterator().get(JK.getColumn()).getPiece() == null) {
                            //System.out.println("Gothere");
                            JK.setPieceType(J.getType());
                            JK.setColor(Piece.color.YELLOW);
                            JK.setPlayer(new Player("dsahjkncakjn cmkndjkanfkajnfjak"));

                            PL.add(JK);
                        }
                    }
                }
            }
        }
        this.HighlightPiece=PL;
    }

    public void AddHighLight(Player p)
    {
        if(p.getColor())
        {
            RedCheat = true;
        }
        else
        {
            WhiteCheat = true;
        }

        this.setHighlightPiece();
        for (Piece PK:HighlightPiece){

            this.getBV().iterator().get(PK.getRow()).iterator().get(PK.getColumn()).addPiece(PK);
        }

        //ReAll();
    }

    public void DeleteHighLight(){
        for(Piece DelPiece:this.HighlightPiece){
            if(this.getBV().iterator().get(DelPiece.getRow()).iterator().get(DelPiece.getColumn()).getPiece() != null){
                if(this.getBV().iterator().get(DelPiece.getRow()).iterator().get(DelPiece.getColumn()).getPiece().getColor() != Piece.color.RED){
                    if(this.getBV().iterator().get(DelPiece.getRow()).iterator().get(DelPiece.getColumn()).getPiece().getColor() != Piece.color.WHITE){
                        this.getBV().iterator().get(DelPiece.getRow()).iterator().get(DelPiece.getColumn()).addPiece(null);

                    }
                }
            }

        }

    }
    public void DeleteHighLight(Player p){

        if(p.getColor())
        {
            RedCheat = false;
        }
        else
        {
            WhiteCheat = false;
        }

        for(Piece DelPiece:this.HighlightPiece){
            if(this.getBV().iterator().get(DelPiece.getRow()).iterator().get(DelPiece.getColumn()).getPiece() != null){
                if(this.getBV().iterator().get(DelPiece.getRow()).iterator().get(DelPiece.getColumn()).getPiece().getColor() != Piece.color.RED){
                    if(this.getBV().iterator().get(DelPiece.getRow()).iterator().get(DelPiece.getColumn()).getPiece().getColor() != Piece.color.WHITE){
                        this.getBV().iterator().get(DelPiece.getRow()).iterator().get(DelPiece.getColumn()).addPiece(null);

                    }
                }
            }

        }

        ReAll();
    }

    public boolean CheckNotRight(int row, int column){
        if (this.getBV().iterator().get(row).iterator().get(column).getPiece().getColor() != (Piece.color.YELLOW)){
            if(this.getBV().iterator().get(row).iterator().get(column).getPiece().getColor() != (Piece.color.GREEN)){
                return true;
            }
        }
        return false;

    }

    public boolean CanThisPlayerJump(Player P){
        for(Piece K : P.getMyPiece()){
            if(K.getSingleJumpPossibles().size() >0){
                return true;
            }
        }
        return false;
    }


    public int getGameNumber()
    {
        return gameNumber;
    }

}
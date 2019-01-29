package com.webcheckers.model;

//import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;

import java.util.ArrayList;

import java.util.Arrays;

public class Piece {
    public enum color{
        RED, WHITE, YELLOW, GREEN
    }

    public enum type
    {
        SINGLE, KING
    }

    /**
     * Top Down on BoardView
     * Use ArrayList<> index to access things</>
     */
    private int row;
    private int column;
    private type pieceType;
    private color Color;
    private Player player;
    private ArrayList<Piece> SingleJumpPossibles = new ArrayList<>();
    private ArrayList<Position> pastLocations = new ArrayList<>();
    private boolean made_a_jump = false;
    private boolean made_a_move = false;
    private ArrayList<Piece> AllPossible = new ArrayList<>();

    public Piece(int row, int column){
        this.row = row;
        this.column = column;
    }

    private ArrayList<Piece>validPiecesArray = new ArrayList<>();

    /**
     * Make a new Piece
     * @param row int
     * @param column int
     * @param COLOR boolean
     * @param player Player
     */
    public Piece(int row, int column, boolean COLOR, Player player){
        this.row = row;
        this.column = column;
        this.pieceType = type.SINGLE;
        if(COLOR){
            this.Color = color.RED;
        }
        else{
            this.Color = color.WHITE;
        }
        this.player = player;
        this.player.addPiece(this);
    }

    public void overRide(Piece c)
    {
        this.row = c.row;
        this.column = c.column;
        this.pieceType = c.pieceType;
        this.Color = c.Color;
        this.player = c.player;
    }

    /**
     * Function to check validity for simple move
     */
    public boolean checkSpaceValidity(BoardView bv, int row, int column){
        // if there is not a piece in space
        if(bv.iterator().get(row).iterator().get(column).getPiece() == null){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * Function to check if space exists
     */
    public boolean checkSpaceExistance(BoardView bv, int row, int column) {
        if(row > 7 || column > 7 || row < 0 || column < 0){
            return false;
        }
        return true;
    }

    /**
     * Function to check if simple move is valid for a SINGLE player
     */
    public void simpleMove(BoardView bv){


        // POSSIBLE MOVES FOR A PIECE
        int R1 = this.row-1;
        int R2 = this.row+1; // POSSIBLE FOR ONLY KING
        int C1 = this.column-1;
        int C2 = this.column+1;

        // TEMP ARRAYLIST FOR STORING POSSIBLE MOVES
        ArrayList<Piece> tempArray = new ArrayList<>();
        this.validPiecesArray = new ArrayList<>();


        // POSSIBLE PIECES
        Piece P1 = new Piece(R1, C1);//down right
        Piece P2 = new Piece(R1, C2);//down left
        Piece P3 = new Piece(R2, C1);//up right
        Piece P4 = new Piece(R2, C2);// up left

        // checking player red or white
        if(this.pieceType.equals(type.KING)){
            tempArray.addAll(Arrays.asList(P1, P2, P3, P4));
        }

        else if(this.getColor() == color.RED && this.pieceType.equals(type.SINGLE)){
            tempArray.addAll(Arrays.asList(P3, P4));
            }
        else if(this.getColor() == color.WHITE && this.pieceType.equals(type.SINGLE)){
            tempArray.addAll(Arrays.asList(P1,P2));
            }

        for(Piece piece: tempArray){
            if(checkSpaceExistance(bv, piece.row, piece.column) && checkSpaceValidity(bv, piece.getRow(), piece.getColumn())){
                validPiecesArray.add(piece);
                }
         }

    }

    /**
     * Returns array pieces
     */
    public ArrayList<Piece> getSimpleMovePossible(){
        return validPiecesArray;
    }


    /**
     * Put all the possible jump move spot into the piece
     * @param IX
     */
    public void setJumpMove(ArrayList<Piece> IX){
        this.SingleJumpPossibles =  IX;
    }

    /**
     * Getters for all the fields
     * @return whatever it says
     */
    public int getRow(){
        return this.row;
    }

    public int getColumn(){
        return this.column;
    }

    public type getType(){
        return this.pieceType;
    }

    public color getColor(){
        return this.Color;
    }

    public Player getPlayer() {
        return player;
    }

    public void kingPiece(){
        this.pieceType = type.KING;
    }

    public void addPastLocation(Position x)
    {
        pastLocations.add(x);
    }

    public ArrayList<Position> getPastLocation() {
        return pastLocations;
    }

    public void resetLocations()
    {
        pastLocations = new ArrayList<>();
    }

    public void goBack()
    {
        if(pastLocations.size() <= 1)
        {
            Position x = pastLocations.get(pastLocations.size()-1);
            Piece b = this.player.getBoard().JumpTempMoves(x, this);
            this.overRide(b);
            pastLocations.remove(pastLocations.size()-1);
            if(pastLocations.size() == 0)
            {
                this.setMadeAMove(false);
                this.setMadeAJump(false);
            }
        }
        else
        {
            Position x = pastLocations.get(pastLocations.size()-2);
            Piece b = this.player.getBoard().JumpTempMoves(x, this);
            this.overRide(b);
            pastLocations.remove(pastLocations.size()-2);
            pastLocations.remove(pastLocations.size()-1);
            if(pastLocations.size() == 0) {
                this.setMadeAMove(false);
                this.setMadeAJump(false);
            }
        }
    }

    public void setRow(int r){
        this.row = r;
    }

    public void setColumn(int c){
        this.column = c;
    }

    public ArrayList<Piece> getSingleJumpPossibles() {
        return SingleJumpPossibles;
    }

    public boolean isEquals(Object P){
        if ( P instanceof Piece ){
            if(((Piece) P).getRow() == this.row && ((Piece) P).getColumn()== this.column){
                return true;
            }
        }
        return false;
    }

    public boolean equals(Object P){
        if ( P instanceof Piece ){
            if(((Piece) P).getRow() == this.row && ((Piece) P).getColumn()== this.column){
                return true;
            }
        }
        return false;
    }

    public String toString(){
        String ns = "";
        int row = this.row;
        int col = this.column;
        ns += row;
        ns += ",";
        ns += col;
        return ns;
    }

    public void SetAllPossible(boolean canJump){
        if(canJump == false){
                this.AllPossible = this.getSimpleMovePossible();
            }


        else{
            this.AllPossible = this.getSingleJumpPossibles();
        }
    }


    public ArrayList<Piece> GetAllPossible(){
        return this.AllPossible;
    }

    public void setPieceType(type t){
        this.pieceType =t;

    }

    public void setColor(color C){
        this.Color = C;
    }
    public void setMadeAJump(boolean made_a_jump) {
        this.made_a_jump = made_a_jump;
    }

    public boolean getMadeAJump()
    {
        return this.made_a_jump;
    }

    public void setMadeAMove(boolean x)
    {
        made_a_move = x;
    }

    public boolean getMadeAMove()
    {
        return this.made_a_move;
    }

    public void setPlayer(Player P){
        this.player = P;
    }
    //
}

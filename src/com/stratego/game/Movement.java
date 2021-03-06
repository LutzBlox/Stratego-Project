//Preferably PPnum is used to end setup well function when it reaches 79.
/*
 NEEDS:
 if(MOUSE REALEASED ON SQUARE WHILE HOLDING GHOST FROM WELL){
 PreparePiece(X OF SQUARE,Y OF SQUARE, RANK, SIDE);   //Square over being the square the mouse or piece or piece is over, which is better interface? 
 }
 if (PIECE CLICKED ON){
 GatherPiece(X OF HOME, Y OF HOME);
 }
 if (MOUSE RELEASED WHILE HeldPiece is not null && MOUSE OVER A SQUARE){
 PlacePiece(X OF SQUARE OVER, Y OF SQUARE OVER);   //Square over being the square the mouse or piece or piece is over, which is better interface? 
 }*/
package com.stratego.game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Movement {

	public static Piece heldPiece = null;

	/** Counter for piece number during generation */
	public static int PPnum = 0;

	public static void preparePiece(int x, int y, int rank, int side) {

		MainGame.gameBoard[x][y].piece = new Piece(x, y, rank, side);
		PPnum++;
	}

	// ?? - This doesn't work
	public static void gatherPiece(int x, int y) {

		// if (MainGame.soldiers[MainGame.gameBoard[x][y].piece.].rank<=10){
		// //no soldiers supposed to be here?
		// heldPiece = gameBoard[x][y].piece;
		// } else {
		// //play error noise?
		// }
	}

	public static Point[] getMoveRange(Piece selected, boolean hasMoved, boolean inSetup) {

		List<Point> pointList = new ArrayList<Point>();

		if(!inSetup){
		
		if (hasMoved) {

			return new Point[] {};
		}

		int x = selected.x;
		int y = selected.y;

		switch (selected.soldierRank) {

		case 9:

			boolean isOnEnemyXM = false;

			for (int lX = x; lX >= 0 && lX < 10; lX--) {

				if (lX != x
						&& (MainGame.gameBoard[lX][y].piece == null || (MainGame.gameBoard[lX][y].piece.soldierSide != selected.soldierSide))
						&& MainGame.gameBoard[lX][y].moveable && !isOnEnemyXM) {

					if (MainGame.gameBoard[lX][y].piece != null) {

						if (MainGame.gameBoard[lX][y].piece.soldierSide != selected.soldierSide) {

							isOnEnemyXM = true;
						}
					}

					pointList.add(new Point(lX, y));

				} else if (lX != x) {

					break;
				}
			}

			boolean isOnEnemyXP = false;

			for (int lX = x; lX >= 0 && lX < 10; lX++) {

				if (lX != x
						&& (MainGame.gameBoard[lX][y].piece == null || (MainGame.gameBoard[lX][y].piece.soldierSide != selected.soldierSide))
						&& MainGame.gameBoard[lX][y].moveable && !isOnEnemyXP) {

					if (MainGame.gameBoard[lX][y].piece != null) {

						if (MainGame.gameBoard[lX][y].piece.soldierSide != selected.soldierSide) {

							isOnEnemyXP = true;
						}
					}

					pointList.add(new Point(lX, y));

				} else if (lX != x) {

					break;
				}
			}

			boolean isOnEnemyYM = false;

			for (int lY = y; lY >= 0 && lY < 10; lY--) {

				if (lY != y
						&& (MainGame.gameBoard[x][lY].piece == null || (MainGame.gameBoard[x][lY].piece.soldierSide != selected.soldierSide))
						&& MainGame.gameBoard[x][lY].moveable && !isOnEnemyYM) {

					if (MainGame.gameBoard[x][lY].piece != null) {

						if (MainGame.gameBoard[x][lY].piece.soldierSide != selected.soldierSide) {

							isOnEnemyYM = true;
						}
					}

					pointList.add(new Point(x, lY));

				} else if (lY != y) {

					break;
				}
			}

			boolean isOnEnemyYP = false;

			for (int lY = y; lY >= 0 && lY < 10; lY++) {

				if (lY != y
						&& (MainGame.gameBoard[x][lY].piece == null || (MainGame.gameBoard[x][lY].piece.soldierSide != selected.soldierSide && !isOnEnemyYM))
						&& MainGame.gameBoard[x][lY].moveable && !isOnEnemyYP) {

					if (MainGame.gameBoard[x][lY].piece != null) {

						if (MainGame.gameBoard[x][lY].piece.soldierSide != selected.soldierSide) {

							isOnEnemyYP = true;
						}
					}

					pointList.add(new Point(x, lY));

				} else if (lY != y) {

					break;
				}
			}

			break;

		case 11:

			break;

		case 12:

			break;

		default:

			Point[] points = new Point[] { new Point(x - 1, y),
					new Point(x, y - 1), new Point(x, y + 1),
					new Point(x + 1, y) };

			for (Point p : points) {

				if (p.x >= 0
						&& p.x <= 9
						&& p.y >= 0
						&& p.y <= 9
						&& MainGame.gameBoard[p.x][p.y].moveable
						&& (MainGame.gameBoard[p.x][p.y].piece == null || MainGame.gameBoard[p.x][p.y].piece.soldierSide != selected.soldierSide)) {

					pointList.add(p);
				}
			}

			break;
		}
		
		}else{
			
			if(selected.soldierSide == 0){
				
				for(int x = 0; x < 10; x++){
					
					for(int y = 0; y < 4; y++){
						
						if(MainGame.gameBoard[x][y].piece == null){
							
							pointList.add(new Point(x, y));
						}
					}
				}
				
			}else if(selected.soldierSide == 1){
				
				for(int x = 0; x < 10; x++){
					
					for(int y = 6; y < 10; y++){
						
						if(MainGame.gameBoard[x][y].piece == null){
							
							pointList.add(new Point(x, y));
						}
					}
				}
			}
		}

		return pointList.toArray(new Point[] {});
	}

	public static boolean canMoveHere(int x, int y, Piece piece, boolean hasMoved, boolean setup) {

		Point[] points = getMoveRange(piece, hasMoved, setup);

		for (Point p : points) {

			if (p.x == x && p.y == y) {

				return true;
			}
		}

		return false;
	}

	public static void placePiece(int x, int y) {

		// Moveable Destination?
		if ((heldPiece.x == x && heldPiece.y == y)
				|| 1 < Math.abs(heldPiece.x - x)
				|| 1 < Math.abs(heldPiece.y - y)) {

			// error noise?

		} else if (MainGame.gameBoard[x][y].piece != null) {

			Combat.engage(heldPiece, MainGame.gameBoard[x][y].piece);

		} else {

			MainGame.gameBoard[x][y].piece = heldPiece;
			MainGame.gameBoard[heldPiece.x][heldPiece.y].piece = null;
		}
		heldPiece = null;
	}
}

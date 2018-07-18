package bowling;

public class SinglePlayerBowlingScoreboardImpl_Thomasson implements SinglePlayerBowlingScoreboard, AssignmentMetaData {



	private static final int MAXIMUM_ROLLS = 21;	
	private String playerName;
	private int[] pinsKnockedDownArray = new int[MAXIMUM_ROLLS];
	private int rollCount = 0;
	
	public SinglePlayerBowlingScoreboardImpl_Thomasson(String playerName)
	{
		assert playerName != null : "playerName is null!";
		this.playerName = playerName;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public int getCurrentFrame() //NOT FINISHED
	{
		if(pinsKnockedDownArray[0] == 10 && rollCount == 1)//if first roll is a strike check to see if is actually  a special case
		{
			return 2;
		}
		
		boolean firstRollInFrame = true;
		int numberOfBoxesPassed = 0;
		int rv = 1;
		
		if(getNumberOfStrikes() == 0) 
		{

			return ((rollCount/2) +1);
		}
		
			
		for(int i = 0; i < rollCount; i++)
		{
			if(pinsKnockedDownArray[i] == 10 && firstRollInFrame == true)
			{
				numberOfBoxesPassed += 2;
				firstRollInFrame = true;
			}
			else 
			{
				if (firstRollInFrame = true)
				{
					firstRollInFrame = false;
					numberOfBoxesPassed +=1;

			
				}
				else if(firstRollInFrame = false)
				{
					firstRollInFrame = true;
					numberOfBoxesPassed +=1;

				}
			}
		}
		if(((numberOfBoxesPassed/2)+1) == 11)
		{
			return 10;
		}
		rv =  (numberOfBoxesPassed/2) +1;	
		return rv;
	}  

	private int getRollIndexOfFirstBall(int frameNumber) 
	{
		int rollIndexOfFirstBall = 0; //position in array
		
		for(int frameNumberThatWeIncrement = 1; frameNumberThatWeIncrement < frameNumber ; frameNumberThatWeIncrement ++)
		{	

			if(pinsKnockedDownArray[rollIndexOfFirstBall] == 10) //checks for strikes
			{
				rollIndexOfFirstBall +=1;
			}
			if(pinsKnockedDownArray[rollIndexOfFirstBall] < 10)
			{
				rollIndexOfFirstBall += 2;
			}

		}
		return rollIndexOfFirstBall; // breaks out of loop when frameNumber = frameNumber
		
	}

	public Mark getMark(int frameNumber, int boxIndex) 
	{	
		assert 1 <= frameNumber : "frameNumber = " + frameNumber + " < 1!";
		assert frameNumber <= 10 : "frameNumber = " + frameNumber + " > 10!";
		assert 1 <= boxIndex : "boxIndex = " + boxIndex + " < 1!";
		assert boxIndex <= 3 : "boxIndex = " + boxIndex + " > 3!";
		int indexInPKDArray = 0;

		if(frameNumber > getCurrentFrame())
		{
		return Mark.EMPTY;
		}

		if(pinsKnockedDownArray[getRollIndexOfFirstBall(frameNumber)] == 10) // if(isStrike(frameNumber)
		{
			if(boxIndex == 1) {
				return Mark.EMPTY;
			}
			if(boxIndex == 2)
			{
				return Mark.STRIKE;	
			}
		}
	
		if(boxIndex == 1) 
		{
			indexInPKDArray = getRollIndexOfFirstBall(frameNumber);
		}
		
		if(boxIndex == 2)
		{
			indexInPKDArray = getRollIndexOfFirstBall(frameNumber)+1;
			
			if(isSpare(frameNumber)) //if spare
				{
					return Mark.SPARE;
				}
		}
		if(boxIndex == 3)
		{
			indexInPKDArray = getRollIndexOfFirstBall(10)+2;
		}
		
		int scoreToConvertToMark = pinsKnockedDownArray[indexInPKDArray];
		
		return Mark.translate(scoreToConvertToMark);//check to see if this works to convert number to mark 
	}

	public int getScore(int frameNumber) {
		assert 1 <= frameNumber : "frameNumber = " + frameNumber + " < 1!";
		assert frameNumber <= 10 : "frameNumber = " + frameNumber + " > 10!";
	//	assert frameNumber <= getCurrentFrame() && !isStrike(frameNumber) && !isSpare(frameNumber): "There is a strike or spare! Score cannot be calculated for this frame yet!";

		int score = 0;
		int bonus = 0;
		int runningPreviousScore = pinsKnockedDownArray[0];
		int pinsKnockedDownInFrame = 0;

		if(rollCount == 0)
			return 0;
		if(getNumberOfStrikes() == 10)
		{
			return 300;
		}
		if(getNumberOfStrikes() == 0 && getNumberOfSpares() == 0) 
		{	
			return (sum(pinsKnockedDownArray, 0, 2*frameNumber));//array, beginning index, end index
		}
		if(frameNumber >1) 
		{	
			runningPreviousScore = getScore(frameNumber-1);
		}
		
		if(frameNumber == 1)//if strike in frame 1
		{
			if(pinsKnockedDownArray[getRollIndexOfFirstBall(1)] == 10)
			{
				pinsKnockedDownInFrame = 10;
				bonus = (pinsKnockedDownArray[getRollIndexOfFirstBall(2)] + pinsKnockedDownArray[getRollIndexOfFirstBall(2)+1]);
							
			}
			return pinsKnockedDownInFrame + bonus;
		}
		for(int i = 0; i <= frameNumber; i++)
		{ 
			if (isStrike(i) == false && isSpare(i) == false)
				{
			pinsKnockedDownInFrame = (pinsKnockedDownArray[getRollIndexOfFirstBall(i)]+ pinsKnockedDownArray[getRollIndexOfFirstBall(i)+1]);
				}
			if(isStrike(i) == true) 
				{
				pinsKnockedDownInFrame = 10;
				bonus = (pinsKnockedDownArray[getRollIndexOfFirstBall(i)+1] + pinsKnockedDownArray[getRollIndexOfFirstBall(i)+2]);
				}
			if(isSpare(i) == true)
				{
				pinsKnockedDownInFrame = 10;
				bonus = pinsKnockedDownArray[getRollIndexOfFirstBall(i)+2];
				}
			
		}
		score = runningPreviousScore + pinsKnockedDownInFrame + bonus;
	
	return  score;
		
	}

	private int getNumberOfSpares()
	{
		int numberOfSpares = 0;
		for(int i = 1; i <= 10; i ++) 
		{
			if(pinsKnockedDownArray[getRollIndexOfFirstBall(i)] + pinsKnockedDownArray[getRollIndexOfFirstBall(i)+1] == 10 ) 
			{
		numberOfSpares += 1;
			}
		}
		return numberOfSpares;
	}
	
	private int getNumberOfStrikes() //checks first ball in each frame, does not check box 2 or 3 in frame 10
	{
		int numberOfStrikes = 0;
		for(int i  = 0; i <= 10; i ++)
		{
			if(pinsKnockedDownArray[getRollIndexOfFirstBall(i)] == 10)
			{
				numberOfStrikes +=1;
			}
		}
		return numberOfStrikes;
	}
	
	private int getNumberOfStrikesAtFrameNumber(int frameNumber) //checks first ball in each frame, does not check box 2 or 3 in frame 10
	{
		int numberOfStrikes = 0;
		for(int i  = 0; i <= frameNumber; i ++)
		{
			if(pinsKnockedDownArray[getRollIndexOfFirstBall(i)] == 10)
			{
				numberOfStrikes +=1;
			}
		}
		return numberOfStrikes;
	}
	
	private static int sum(int[] intArray, int beginIndex, int endIndex)
	{
		assert beginIndex >= 0 : "beginIndex = " + beginIndex + " < 0!";
		assert endIndex <= (intArray.length) : "endIndex = " + endIndex + " > " + (intArray.length) + " = (intArray.length)!";
		assert beginIndex < endIndex : "beginIndex = " + beginIndex + " > " + endIndex + " = endIndex!";
		int sum = 0;
		for(int i = beginIndex; i < endIndex; i++)
		{
			sum = sum + intArray[i];
		}
		return sum;
	}
	
	public boolean isGameOver() 
	{

		if(getCurrentFrame() < 10)
			return false;
		int rollIndexOfFirstBall = getRollIndexOfFirstBall(10);
		if(rollIndexOfFirstBall + 2 == rollCount) //10th frame, 3 rolls
			return true;
		boolean isStrikeInBoxOne = (pinsKnockedDownArray[rollIndexOfFirstBall]== 10);
		if(isStrikeInBoxOne && pinsKnockedDownArray[rollIndexOfFirstBall+1] < 10 && rollCount == rollIndexOfFirstBall +1) //10frame: |X , #<10| & Frame 10, box 2 has been rolled
		{
			return true;
		}
		else
			return false;
	}

	public void recordRoll(int pinsKnockedDown) 
	{
		assert 0 <= pinsKnockedDown : "pinsKnockedDown = " + pinsKnockedDown + " < 0!";
		assert pinsKnockedDown <= 10 : "pinsKnockedDown = " + pinsKnockedDown + " > 10!";
		assert (getCurrentBall() == 1) || 
				((getCurrentBall() == 2) && (((getCurrentFrame() == 10) && isStrikeOrSpare(getMark(10, 1))) || ((pinsKnockedDownArray[rollCount - 1] + pinsKnockedDown) <= 10))) || 
				((getCurrentBall() == 3) && (((getCurrentFrame() == 10) && isStrikeOrSpare(getMark(10, 2))) || ((pinsKnockedDownArray[rollCount - 1] + pinsKnockedDown) <= 10)));
		assert !isGameOver() : "Game is over!";

		pinsKnockedDownArray[rollCount] = pinsKnockedDown;
		rollCount++;
	}

	public int getCurrentBall() {
		assert !isGameOver() : "Game is over!";
		if(rollCount == 0)
			return 1;
		
		if(pinsKnockedDownArray[rollCount-1] == 10)//if strike
			{
			return 1; 
			}
		
		return rollCount%2+1;
		
		}

	//part of pre: getCurrentFrameNumber()> frameNumber (dont' ask about a frame that hasn't been rolled yet)
	//no strikes at frame number
	private boolean isStrike(int frameNumber) //1-9
	
	{
		assert (getCurrentFrame() >= frameNumber): "You are asking about a frame number that is greater than the current frame number!";
		
		if(getCurrentFrame() == frameNumber && getCurrentBall() == 1 && pinsKnockedDownArray[getRollIndexOfFirstBall(frameNumber)] == 10) 
		{
			return true;
		}
		if (getCurrentFrame() == frameNumber && getCurrentBall() == 2)
		{
			assert getCurrentBall() == 2: "Current Ball is 2, this frame is not complete!!";
		}
		
		int indexOfFirstBall = getRollIndexOfFirstBall(frameNumber);
			
		return pinsKnockedDownArray[indexOfFirstBall] == 10;
	}
	//part of pre: getCurrentFrameNumber() > frameNumber 
	private boolean isSpare(int frameNumber) //1-9
	{
		if(isStrike(frameNumber))
			{return false;
			}
		int indexOfFirstBall = getRollIndexOfFirstBall(frameNumber);

		return (pinsKnockedDownArray[indexOfFirstBall]+ pinsKnockedDownArray[indexOfFirstBall+1]) == 10;
	}

	private boolean isStrikeOrSpare(Mark mark) 
	{
		return (mark == Mark.STRIKE || mark == Mark.SPARE);
		
	}
	
	private static final String VERTICAL_SEPARATOR = "#";
	private static final String HORIZONTAL_SEPARATOR = "#";
	private static final String LEFT_EDGE_OF_SMALL_SQUARE = "[";
	private static final String RIGHT_EDGE_OF_SMALL_SQUARE = "]";

	private String getScoreboardDisplay()
	{
		StringBuffer frameNumberLineBuffer = new StringBuffer();
		StringBuffer markLineBuffer = new StringBuffer();
		StringBuffer horizontalRuleBuffer = new StringBuffer();
		StringBuffer scoreLineBuffer = new StringBuffer();
		frameNumberLineBuffer.append(VERTICAL_SEPARATOR);
		
		markLineBuffer.append(VERTICAL_SEPARATOR);
		horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
		scoreLineBuffer.append(VERTICAL_SEPARATOR);

		for(int frameNumber = 1; frameNumber <= 9; frameNumber++)
		{
			frameNumberLineBuffer.append("  " + frameNumber + "  ");
			markLineBuffer.append(" ");
			markLineBuffer.append(getMark(frameNumber, 1));
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(frameNumber, 2));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			
			final int CHARACTER_WIDTH_SCORE_AREA = 5;
			for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) horizontalRuleBuffer.append(HORIZONTAL_SEPARATOR);
			if(isGameOver() || frameNumber < getCurrentFrame())
			{
				int score = getScore(frameNumber);
				final int PADDING_NEEDED_BEHIND_SCORE = 1;
				final int PADDING_NEEDED_IN_FRONT_OF_SCORE = CHARACTER_WIDTH_SCORE_AREA - ("" + score).length() - PADDING_NEEDED_BEHIND_SCORE;
				for(int i = 0; i < PADDING_NEEDED_IN_FRONT_OF_SCORE; i++) scoreLineBuffer.append(" ");
				scoreLineBuffer.append(score);
				for(int i = 0; i < PADDING_NEEDED_BEHIND_SCORE; i++) scoreLineBuffer.append(" ");
			}
			else
			{
				for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) scoreLineBuffer.append(" ");
			}
			
			frameNumberLineBuffer.append(VERTICAL_SEPARATOR);
			markLineBuffer.append(VERTICAL_SEPARATOR);
			horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
			scoreLineBuffer.append(VERTICAL_SEPARATOR);
		}
		//Frame 10:
		{
			final String THREE_SPACES = "   ";
			frameNumberLineBuffer.append(THREE_SPACES + 10 + THREE_SPACES);

			markLineBuffer.append(" ");
			markLineBuffer.append(getMark(10, 1));
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(10, 2));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(LEFT_EDGE_OF_SMALL_SQUARE);
			markLineBuffer.append(getMark(10, 3));
			markLineBuffer.append(RIGHT_EDGE_OF_SMALL_SQUARE);
			
			final int CHARACTER_WIDTH_SCORE_AREA = 8;
			for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) horizontalRuleBuffer.append(HORIZONTAL_SEPARATOR);
			if(isGameOver())
			{
				int score = getScore(10);
				final int PADDING_NEEDED_BEHIND_SCORE = 1;
				final int PADDING_NEEDED_IN_FRONT_OF_SCORE = CHARACTER_WIDTH_SCORE_AREA - ("" + score).length() - PADDING_NEEDED_BEHIND_SCORE;
				for(int i = 0; i < PADDING_NEEDED_IN_FRONT_OF_SCORE; i++) scoreLineBuffer.append(" ");
				scoreLineBuffer.append(score);
				for(int i = 0; i < PADDING_NEEDED_BEHIND_SCORE; i++) scoreLineBuffer.append(" ");
			}
			else
			{
				for(int i = 0; i < CHARACTER_WIDTH_SCORE_AREA; i++) scoreLineBuffer.append(" ");
			}
			
			frameNumberLineBuffer.append(VERTICAL_SEPARATOR);
			markLineBuffer.append(VERTICAL_SEPARATOR);
			horizontalRuleBuffer.append(VERTICAL_SEPARATOR);
			scoreLineBuffer.append(VERTICAL_SEPARATOR);
		}
			
		return 	getPlayerName() + "\n" +
				horizontalRuleBuffer.toString() + "\n" +
				frameNumberLineBuffer.toString() + "\n" +
				horizontalRuleBuffer.toString() + "\n" +
				markLineBuffer.toString() + "\n" +
				scoreLineBuffer.toString() + "\n" +
				horizontalRuleBuffer.toString();
	}
	
	public String toString()
	{
		return getScoreboardDisplay();
	}
	
	
	//*************************************************
	//*************************************************
	//*************************************************
	//*********ASSIGNMENT METADATA STUFF***************
	public String getFirstNameOfSubmitter() 
	{
		return "Caley";
	}

	public String getLastNameOfSubmitter() 
	{
		return "Thomasson"; 	
	}
	
	public double getHoursSpentWorkingOnThisAssignment()
	{
		return 14.0;
		}
	
	public int getScoreAgainstTestCasesSubset()
	{
		return 3;
	}
}
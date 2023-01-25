
enum Player {
	One {
        public String toString(){
            return "X   ";
        }
    }, 
	Two {
        public String toString(){
            return "O   ";
        }
    };	
    
    Player getOpponent()
    {
    	return (this == One) ? Two : One;
    }
};


function populate(array1, array2, length)
{
	var randomIndex;
	while (array2.length !== length) 
	{
		randomIndex = Math.floor(Math.random()*array1.length);
		if($.inArray(array1[randomIndex],array2) === -1)
		array2.push(array1[randomIndex]);
	}
	return array2;
}
function shuffleArray(array)
{
	var i,j,temp;
	for (i = array.length - 1; i > 0; i--) 
	{
	    j = Math.floor(Math.random() * (i + 1));
	    temp = array[i];
	    array[i] = array[j];
	    array[j] = temp;
	}
	return array;
}
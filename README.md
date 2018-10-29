In	this	project	I created	tab	bar	application	with	2	tabs.	

The	first	tab	present	the	following	information:	
• My	name	
• The	current	Date	and	Time	(should	be updated	dynamically	every	second)	
• An	Empty	label	

The	second	tab	contains	two	inner tabs:	
• The	first	tab	contains	a	table	that	will	show	the	information	received	from	this	RSS	feed	http://feeds.reuters.com/reuters/businessNews
• The	second	tab	contains	a	table	which	is	a	unification	of	the	data	received	from	http://feeds.reuters.com/reuters/entertainment	and	http://feeds.reuters.com/reuters/environment.	First	the	items	from	“Entertainment”	should	be	presented,	and	then	the	items	from	“Environment”		


Features:	
1. Selecting	a	feed	(item)	will	push	a	new	view	with	the	description	of	the	feed.	
2. The	application	should	now	present	in	the	empty	label	of	the	first	tab,	the	title	from	the	feed	that	was	selected	in	the	second	tab.	
4. The	application	should	check	each	RSS	source	(there	are	3	RSS	sources)	every	5	seconds	for	an	update	and	the	UI	should	be	updated	immediately	as	soon	as	one	of	the	RSS	sources	provided	updated	information.		
5. Whenever	the	application	checks	for	update,	it	shows	an	activity	indicator	that	does	not	block	the	screen	or	the	user	from	interacting	with	the	application.	

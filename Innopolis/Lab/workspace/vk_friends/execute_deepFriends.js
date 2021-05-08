var targets = Args.targets;
var all_friends = {};
var req;
var itr = 0;
var lst = targets.split(",");

// из строки с целями вынимаем каждую цель
while (itr <= lst.length) {
	// сразу делаем запросы, как только вытащили id
	req = API.friends.get({"user_id":lst[itr]});
	
	if (req) {
		all_friends = all_friends + [req];
	} else {
		all_friends = all_friends + [0];
	}

    itr = itr + 1;
}
return all_friends;

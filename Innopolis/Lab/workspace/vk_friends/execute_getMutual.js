var source = Args.source;
var targets = Args.targets;
var common_friends = {};
var req;
var itr = 0;
var lst = targets.split(",");

// из строки с целями вынимаем каждую цель
while (itr <= lst.length - 1){
	// сразу делаем запросы, как только вытащили id
	req = API.friends.getMutual({"source_uid":source, "target_uid":lst[itr]});
	
	if (req) {
		common_friends = common_friends + [req];
	} else {
		common_friends = common_friends + [0];
	}

    itr = itr + 1;
}
return common_friends;

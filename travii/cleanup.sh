echo "Cleaning up build folder"
if [-d $HOME/build ]; then
	rm -rf $HOME/release/*

fi
echo "Done!"
echo ""
echo "Cleaning up releaces folder"

if [ ! -d /home/mlzboy/b2c2/shared/db ]; then
	mkdir -p $HOME/release
else
	rm -rf $HOME/release/*
fi
echo "Done!"


echo "Building forge version 1614"
# 1614
cd forge
git checkout 9274e4fe435cb415099a8216c1b42235f185443e
cd ..
git pull origin 1.7.10
./gradlew --parallel -PofficialBuild clean setupCauldron --parallel installbundle


package ovh.not.javamusicbot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.core.entities.TextChannel;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TrackScheduler extends AudioEventAdapter {
    private final GuildMusicManager musicManager;
    private final AudioPlayer player;
    TextChannel textChannel;
    public final Queue<AudioTrack> queue;
    public boolean repeat = false;

    TrackScheduler(GuildMusicManager musicManager, AudioPlayer player, TextChannel textChannel) {
        this.musicManager = musicManager;
        this.player = player;
        this.textChannel = textChannel;
        this.queue = new LinkedList<>();
    }

    @SuppressWarnings("unchecked")
    public void queue(AudioTrack track, boolean... first) {
        if (!player.startTrack(track, true)) {
            if (first != null && first.length > 0 && first[0]) {
                ((List<AudioTrack>) queue).add(0, track);
            } else {
                queue.offer(track);
            }
        }
    }

    public void next(AudioTrack last) {
        AudioTrack track;
        if (repeat && last != null) {
            track = last.makeClone();
        } else {
            track = queue.poll();
        }
        if (!player.startTrack(track, false)) {
            musicManager.close();
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext) {
            next(track);
        }
    }

    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track) {
        textChannel.sendMessage(String.format("", track.getInfo().title,
                track.getInfo().author, Utils.formatDuration(track.getDuration()))).complete();
    }
}
